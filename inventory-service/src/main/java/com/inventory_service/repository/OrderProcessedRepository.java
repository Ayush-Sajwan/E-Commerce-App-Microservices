package com.inventory_service.repository;

import com.inventory_service.entity.OrderProcessedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderProcessedRepository extends JpaRepository<OrderProcessedEntity,Integer> {

    @Query(value = """
            Select * from order_processed
            where order_id= :orderId
            and product_id= :productId
            """,nativeQuery = true)
    OrderProcessedEntity getProcessedOrderByOrderIdAndProductId(
            @Param("orderId") Integer orderId,@Param("productId") Integer productId);
}
