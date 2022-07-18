package com.mywebsite.webbanhang.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from category c where c.name like %:keyword% or c.code like %:keyword%", nativeQuery = true)
    List<Category> findByKeyword(@Param("keyword") String keyword);
}
