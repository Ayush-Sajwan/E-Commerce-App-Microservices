package com.payment_service.repository;

import com.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Query(value = """
            Select * from payments
            where order_id=:orderId
            """, nativeQuery = true)
    Payment getPaymentByOrderId(@Param("orderId") Integer orderId);

    @Query(value = """
            Select * from payments
            where order_id=:orderId
            and id=:id
            """, nativeQuery = true)
    Payment getPaymentByOrderIdAndId(@Param("orderId") Integer orderId,@Param("id") Integer id);
}
