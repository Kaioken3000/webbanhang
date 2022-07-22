package com.mywebsite.webbanhang.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mywebsite.webbanhang.model.Cart;
import com.mywebsite.webbanhang.repository.CartRepository;

@Service
public class CartService {

    CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> listAllCart() {
        return cartRepository.findAll();
    }

    public List<Cart> listCartByUserLoginId(long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addCart(Cart cart){
        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart){
        return cartRepository.save(cart);
    }

    public void deleteCartById(long id){
        cartRepository.deleteById(id);
    }

    public void deleteAllCartByUserId(long userId){
        List<Cart> list = cartRepository.findByUserId(userId);
        Iterator<Cart> iterator = list.iterator();
        while(iterator.hasNext()){
            Cart cart = iterator.next();
            cartRepository.deleteById(cart.getId());
        }
    }

    public Cart getCartByItemIdAndUserId(long userId, long itemId){
        return cartRepository.findCartByUserIdAndItemId(userId, itemId);
    }

    public Cart getCartById(long id){
        return cartRepository.findById(id).get();
    }
 
    public Page<Cart> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.cartRepository.findAll(pageable);
    }  
}
