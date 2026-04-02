package com.order_service.repository;

import com.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value = """
            Select id from orders
            where status=:status
            and created_at< :time
            Limit :limit
            for update skip locked
            """,
    nativeQuery = true)
    List<Integer> findPaymentPendingIds(@Param("limit") Integer limit, @Param("time")LocalDateTime time, @Param("status") String status);


}
