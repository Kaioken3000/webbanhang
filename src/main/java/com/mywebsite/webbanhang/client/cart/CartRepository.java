package com.mywebsite.webbanhang.client.cart;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    @Query(value = "select * from cart c where c.user_id like %:userId% ",
                    nativeQuery = true)
    List<Cart> findByUserId(long userId);

    @Query(value = "SELECT * FROM cart c WHERE c.user_id LIKE %:userId% and EXISTS"+
                        " (SELECT item_id FROM cart_item ci WHERE ci.item_id LIKE %:itemId% AND c.id = ci.cart_id)",
        nativeQuery = true)
    Cart findCartByUserIdAndItemId(long userId, long itemId);
}
