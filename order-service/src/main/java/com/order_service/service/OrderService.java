package com.order_service.service;

import com.order_service.dto.*;
import com.order_service.entity.Order;
import com.order_service.entity.OrderItems;
import com.order_service.exception.InterMSCommException;
import com.order_service.exception.OrderProcessingException;
import com.order_service.feignClients.InventoryClient;
import com.order_service.feignClients.PaymentClient;
import com.order_service.feignClients.ProductClient;
import com.order_service.mapper.OrderItemsMapper;
import com.order_service.mapper.OrderMapper;
import com.order_service.repository.OrderItemsRepository;
import com.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger=LoggerFactory.getLogger(OrderService.class);

    private OrderItemsRepository orderItemsRepository;
    private OrderRepository orderRepository;

    private ProductClient productClient;
    private InventoryClient inventoryClient;
    private PaymentClient paymentClient;

    private KafkaService kafkaService;

    public OrderService(OrderItemsRepository orderItemsRepository,OrderRepository orderRepository,ProductClient productClient,InventoryClient inventoryClient,
                        PaymentClient paymentClient,
                        KafkaService kafkaService){
        this.orderRepository=orderRepository;
        this.orderItemsRepository=orderItemsRepository;
        this.productClient=productClient;
        this.inventoryClient=inventoryClient;
        this.paymentClient=paymentClient;
        this.kafkaService=kafkaService;

        logger.info("Order-Service class successfully initialized and running");
    }


    public OrderWrapperDto processOrder(OrderWrapperDto orderWrapperDto){

        logger.info("Starting the process of processing the order.......");

        //getting the userId
        logger.info("Getting the userId.......");
        Long userId;
        try{
            Jwt jwt=((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getToken();
            userId=(Long) (jwt.getClaim("UserId"));

        }catch (Exception e){
            logger.info("Unable to get userId reason:"+e.getMessage());
            throw new OrderProcessingException("Unable to determine the userId");
        }

        if(userId==null) throw new OrderProcessingException("Unable to determine the source of userId");

        logger.info("Got the userId:"+userId);

        //first lets get the details of all the products
        HashMap<Integer,ProductDto> map=new HashMap<>();

        try{
            for(OrderItemsDto orderItemsDto:orderWrapperDto.orderItemsDto){

                GeneralResponseDto<ProductDto> productResponseDto=this.productClient.getProductById(orderItemsDto.getProductId());
                logger.info(productResponseDto.getData().toString()+"");
                map.put(orderItemsDto.getProductId(),productResponseDto.getData());
            }
        }catch (Exception e){
            logger.info("Was not able to get product details reason: "+e.getMessage());
            throw new InterMSCommException("Unable to get product details please try again later...");
        }

        logger.info("Got the price of all the products");

        List<OrderItems> orderItemsList=new ArrayList<>();

        //calculating the price items wise
        for(OrderItemsDto orderItemsDto:orderWrapperDto.orderItemsDto){

            //creating the entity
            OrderItems orderItems= OrderItemsMapper.getOrderItemsEntity(orderItemsDto);

            //getting the fetched product details
            ProductDto productDto=map.get(orderItemsDto.getProductId());

            //setting the fetched product details
            orderItems.setProductName(productDto.getName());
            orderItems.setPrice(productDto.getPrice().doubleValue());

            //setting the final price for the item
            orderItems.setTotal_price(orderItemsDto.getQuantity()*productDto.getPrice().doubleValue());

            //adding entity in the list
            orderItemsList.add(orderItems);
        }

        //calculating the total final price of the order
        Double price=0.0;
        for(OrderItems orderItems:orderItemsList){
            price+=orderItems.getTotal_price();
        }

        //preparing the order entity
        Order order= new Order();
        order.setTotalAmount(price);
        order.setUserId(userId.intValue());
        order.setStatus("PAYMENT_PENDING");


        //now trying to reserve the stock before saving the items
        try{
            logger.info("Trying to reserve the stock.......");

            for(OrderItemsDto orderItems:orderWrapperDto.orderItemsDto){
                GeneralResponseDto<String> response=this.inventoryClient.reserveStockInventory(orderItems.getProductId(),orderItems.getQuantity());
                logger.info("Successfully reserved: {productId:"+orderItems.getProductId()+" ,quantity:"+orderItems.getQuantity()+"} ");
            }

        }catch (Exception e){
            logger.info("Was not able to reserve the stock reason: "+e.getMessage());
            throw new InterMSCommException("Unable to reserve the stock please try again later...");

        }
        logger.info("Successfully reserved the stock for the order........");

        //saving the order details
        order=this.orderRepository.save(order);

        //now setting the order id in all the order items
        for(OrderItems orderItems:orderItemsList){
            orderItems.setOrderId(order.getId());
        }

        //saving the order item details
        orderItemsList=this.orderItemsRepository.saveAll(orderItemsList);

        logger.info("Successfully saved the order......");

        //now creating the payment details
        try{
            PaymentDto paymentDto=new PaymentDto();
            paymentDto.setAmount(BigDecimal.valueOf(order.getTotalAmount()));
            paymentDto.setOrderId(order.getId());
            paymentDto.setCurrency("INR");
            paymentDto.setStatus("PAYMENT-CREATED");

            GeneralResponseDto<PaymentDto> generalResponseDto=this.paymentClient.createPayment(paymentDto);

            //setting the payment details in order wrapper response
            orderWrapperDto.setPaymentDto(generalResponseDto.getData());


        }catch (Exception e){
            logger.info("Error occurred while creating payment...");
            throw new InterMSCommException("Error occurred while creating the payment request.........");
        }

        orderWrapperDto.setOrderDto(OrderMapper.getOrderDto(order));

        List<OrderItemsDto> orderItemsDtoList=new ArrayList<>();
        for(OrderItems orderItems:orderItemsList){
            OrderItemsDto orderItemsDto=OrderItemsMapper.getOrderItemsDto(orderItems);
            orderItemsDtoList.add(orderItemsDto);
        }
        orderWrapperDto.setOrderItemsDto(orderItemsDtoList);

        return orderWrapperDto;
    }

    //this method will be responsible for cancelling the order and releasing the stock by pushing the stock release event
    public void cancelOrder(Integer orderId,String reason,String statusUpdated){

        //first lets the fetch the order details
        Optional<Order> order=this.orderRepository.findById(orderId);

        if(order.isEmpty()){
            logger.info("Order with orderId:{} not present unable to cancel the order: ",orderId);
            return;
        }

        List<OrderItems> orderItemsList=this.orderItemsRepository.getOrderItemsByOrderId(orderId);

        if(orderItemsList==null || orderItemsList.isEmpty()){
            logger.info("Not able to find the order details of orderId:{}",orderId);
            return;
        }

        logger.info("Got all the details that needed for cancelling the orderId:{}",orderId);

        //sending the inventory release events for all the product ids of the current order
        for(OrderItems orderItems:orderItemsList){
            this.kafkaService.inventoryRelease(orderId,orderItems.getProductId(),orderItems.getQuantity(),reason);
        }
        logger.info("Successfully pushed the inventory release for the orderId:{}",orderId);

        //now setting the final status in db
        order.ifPresent(order1 -> {
            order1.setStatus(statusUpdated);
            this.orderRepository.save(order1);
        });

        logger.info("Successfully cancelled the order with order id: {}",orderId);

    }


    public void confirmOrder(Integer orderId,String reason,String statusUpdated){

        //first lets the fetch the order details
        Optional<Order> order=this.orderRepository.findById(orderId);

        if(order.isEmpty()){
            logger.info("Order with orderId:{} not present unable to confirm the order: ",orderId);
            return;
        }

        List<OrderItems> orderItemsList=this.orderItemsRepository.getOrderItemsByOrderId(orderId);

        if(orderItemsList==null || orderItemsList.isEmpty()){
            logger.info("Not able to find the order details of orderId:{}",orderId);
            return;
        }

        logger.info("Got all the details that needed for confirming the orderId:{}",orderId);

        //sending the inventory release events for all the product ids of the current order
        for(OrderItems orderItems:orderItemsList){
            this.kafkaService.inventoryDeduct(orderId,orderItems.getProductId(),orderItems.getQuantity(),reason);
        }
        logger.info("Successfully pushed the inventory deduct for the orderId:{}",orderId);

        //now setting the final status in db
        order.ifPresent(order1 -> {
            order1.setStatus(statusUpdated);
            this.orderRepository.save(order1);
        });

        logger.info("Successfully confirmed the order with order id: {}",orderId);

    }



}
