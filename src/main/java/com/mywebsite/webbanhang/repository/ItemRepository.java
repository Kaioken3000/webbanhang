package com.mywebsite.webbanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mywebsite.webbanhang.model.Item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
    @Query(value = "select * from item i where i.name like %:keyword% "
                    +" or i.price like %:keyword%" 
                    +" or i.info like %:keyword%" 
                    +" or i.publisher like %:keyword%" 
                    +" or i.size like %:keyword%" 
                    +" or i.number_in_store like %:keyword%"
                    +" or i.id like %:keyword%",
                    nativeQuery = true)
    List<Item> findByKeyword(@Param("keyword") String keyword);
}
