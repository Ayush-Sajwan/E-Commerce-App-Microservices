package com.order_service.repository;

import com.order_service.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Integer> {

    @Query(value = """
            Select * from order_items
            where order_id=:orderId
            """,nativeQuery = true)
    List<OrderItems> getOrderItemsByOrderId(@Param("orderId") Integer orderId);
}
