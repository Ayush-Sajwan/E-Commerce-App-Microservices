package com.product.repository;

import com.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = "select * from categories where id=:id",nativeQuery = true)
    Category getCategoryById(@Param("id") int id);
}
