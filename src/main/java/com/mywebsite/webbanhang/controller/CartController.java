package com.mywebsite.webbanhang.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.mywebsite.webbanhang.model.Cart;
import com.mywebsite.webbanhang.model.CustomOAuth2User;
import com.mywebsite.webbanhang.model.User;
import com.mywebsite.webbanhang.service.CartService;
import com.mywebsite.webbanhang.service.UserService;

@Controller
public class CartController {

    CartService cartService;
    UserService userService;
    HttpServletRequest request;
    
    public CartController(CartService cartService, UserService userService, HttpServletRequest request) {
        this.cartService = cartService;
        this.userService = userService;
        this.request = request;
    }

    public User getUser_(Authentication authentication){
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByEmail(principal.getName());
        if(user == null ){
            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            user = userService.getUserByEmail(oauthUser.getEmail());
        }
        return user;
    }

    @GetMapping("/client/cart")
    public String showCartPage(Model model, Authentication authentication){
        User user = getUser_(authentication);
        model.addAttribute("listCarts", cartService.listCartByUserLoginId(user.getId()));
        return "/client/cart";
    }

    @PostMapping("/client/cart/addCart/{itemCart}")
    public RedirectView addCart(@PathVariable long itemCart, @ModelAttribute("cart") Cart cart, Authentication authentication){
        User user = getUser_(authentication);

        cart.setUser(user);
        cart.setHasAddCart(true);
        cart.setHasBuy(false);
        cartService.addCart(cart);
        String referer = request.getHeader("Referer");
        return new RedirectView(referer);
    }

    @GetMapping("/client/cart/deleteCart/{id}")
    public String deleteCart(@PathVariable long id){
        cartService.deleteCartById(id);
        return "redirect:/client/cart";
    }

    @GetMapping("/client/cart/deleteAllUserCart")
    public String deleteAllUserCart(Authentication authentication){
        User user = getUser_(authentication);

        cartService.deleteAllCartByUserId(user.getId());
        return "redirect:/client/cart";
    }

    @GetMapping("/client/cart/updateCart/{id}/item/{itemId}/number/{number}")
    public String updateCart(@PathVariable("id") long id, @PathVariable("itemId") long itemId, 
    @PathVariable("number") int number,  @ModelAttribute("listCarts") Cart cart ,
    Authentication authentication){
        Cart extingCart = cartService.getCartById(id);
        extingCart.setId(cart.getId());
        extingCart.setNumber(number);

        User user = getUser_(authentication);
        Cart c = cartService.getCartByItemIdAndUserId(user.getId(), itemId);
        extingCart.setItemCart(c.getItemCart());
        extingCart.setUser(user);

        cartService.updateCart(extingCart);

        return "redirect:/client/cart";
    }
}
