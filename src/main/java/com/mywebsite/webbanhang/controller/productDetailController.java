package com.mywebsite.webbanhang.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mywebsite.webbanhang.model.Cart;
import com.mywebsite.webbanhang.model.User;
import com.mywebsite.webbanhang.service.CartService;
import com.mywebsite.webbanhang.service.ItemService;
import com.mywebsite.webbanhang.service.UserService;

@Controller
public class productDetailController {
    ItemService itemService;
    CartService cartService;
    HttpServletRequest request;
    UserService userService;

    public productDetailController(ItemService itemService, CartService cartService, HttpServletRequest request,
            UserService userService) {
        this.itemService = itemService;
        this.cartService = cartService;
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/client/showProductDetail/{id}")
    public String showProductDetailPage(@PathVariable long id, Model model) {
        model.addAttribute("item", itemService.getItemById(id));
        model.addAttribute("itemLists", itemService.listAllItem());

        try {
            Principal principal = request.getUserPrincipal();
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("listCarts", cartService.listCartByUserLoginId(user.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Cart cart = new Cart();
        model.addAttribute("cart", cart);
        return "/client/product-details";
    }
}
