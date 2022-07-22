package com.mywebsite.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mywebsite.webbanhang.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from category c where c.name like %:keyword% or c.code like %:keyword%", nativeQuery = true)
    List<Category> findByKeyword(@Param("keyword") String keyword);
}
