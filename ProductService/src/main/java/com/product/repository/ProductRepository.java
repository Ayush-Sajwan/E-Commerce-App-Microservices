package com.product.repository;

import com.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "select * from products where id=:id",nativeQuery = true)
    Product getProductById(@Param("id") int id);

    @Query(value = """
        select * from products
        where category_id = :categoryId
        and (:cursorId is null or id > :cursorId)
        order by id asc
        limit :size
        """,
            nativeQuery = true)
    List<Product> getProductsByCategory(@Param("categoryId") Integer categoryId,
                                        @Param("cursorId") Integer cursorId,
                                        @Param("size") Integer size
                                        );

    @Query(value = """
        select * from products
        where name like CONCAT('%' ,:name,'%')
        and (:cursorId is null or id > :cursorId)
        order by id asc
        limit :size
        """,
            nativeQuery = true)
    List<Product> getProductsByName(@Param("name") String name,
                                        @Param("cursorId") Integer cursorId,
                                        @Param("size") Integer size
    );
}
