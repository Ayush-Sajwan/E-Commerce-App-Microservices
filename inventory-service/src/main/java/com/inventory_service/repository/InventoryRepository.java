package com.inventory_service.repository;

import com.inventory_service.entity.Inventory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Integer> {

    @Query(value = """ 
            select * from inventory
            where product_id=:product_id
            """,nativeQuery = true)
    Inventory getInventoryByProductId(@Param("product_id") Integer product_id);


    @Query(value = """ 
            select * from inventory
            where id=:id
            """,nativeQuery = true)
    Inventory getInventoryById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = """ 
            update inventory
            set available_quantity= available_quantity - :quantity,
            reserved_quantity= reserved_quantity + :quantity
            where product_id= :product_id
            and available_quantity>= :quantity
            """,nativeQuery = true)
    int reserveStockInInventory(@Param("product_id") Integer product_id,@Param("quantity") Integer quantity);

    @Modifying
    @Transactional
    @Query(value = """ 
            update inventory
            set available_quantity= available_quantity + :quantity,
            reserved_quantity= reserved_quantity - :quantity
            where product_id= :product_id
            """,nativeQuery = true)
    int releaseStockInInventory(@Param("product_id") Integer product_id,@Param("quantity") Integer quantity);

    @Modifying
    @Transactional
    @Query(value = """ 
            update inventory
            set reserved_quantity= reserved_quantity - :quantity
            where product_id= :product_id
            """,nativeQuery = true)
    int deductStockInInventory(@Param("product_id") Integer product_id,@Param("quantity") Integer quantity);
}
