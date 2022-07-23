package com.mywebsite.webbanhang.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

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
import com.mywebsite.webbanhang.model.Item;
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

    public User getUser_(Authentication authentication) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByEmail(principal.getName());
        if (user == null) {
            CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
            user = userService.getUserByEmail(oauthUser.getEmail());
        }
        return user;
    }

    @GetMapping("/client/cart")
    public String showCartPage(Model model, Authentication authentication) {
        User user = getUser_(authentication);
        model.addAttribute("listCarts", cartService.listCartByUserLoginId(user.getId()));
        return "/client/cart";
    }

    @PostMapping("/client/cart/addCart/{itemCart}")
    public RedirectView addCart(@PathVariable long itemCart, @ModelAttribute("cart") Cart cart,
            Authentication authentication) {
        User user = getUser_(authentication);

        List<Cart> listCart = cartService.listCartByUserLoginId(user.getId());
        Iterator<Cart> itr = listCart.iterator();
        Cart c = new Cart();

        //kiem tra da co trong cart hay chua
        boolean kt = false;
        long idCart = 0;
        while (itr.hasNext()) {
            c = (Cart) itr.next();
            Iterator<Item> i = c.getItemCart().iterator();
            while (i.hasNext()) {
                Item it = i.next();
                System.out.println(it.getId());
                if (it.getId() == itemCart) {
                    idCart = c.getId();
                    kt=true;
                    break;
                }
            }
        }

        
        if (kt == true) {
            Cart extingCart = cartService.getCartById(idCart);
            extingCart.setId(idCart);
            extingCart.setNumber(cart.getNumber());
            extingCart.setUser(user);
            cartService.updateCart(extingCart);
        }
        else{
            cart.setUser(user);
            cart.setHasAddCart(true);
            cart.setHasBuy(false);
            cartService.addCart(cart);
        }

        String referer = request.getHeader("Referer");
        return new RedirectView(referer);
    }

    @GetMapping("/client/cart/deleteCart/{id}")
    public String deleteCart(@PathVariable long id) {
        cartService.deleteCartById(id);
        return "redirect:/client/cart";
    }

    @GetMapping("/client/cart/deleteAllUserCart")
    public String deleteAllUserCart(Authentication authentication) {
        User user = getUser_(authentication);

        cartService.deleteAllCartByUserId(user.getId());
        return "redirect:/client/cart";
    }

    @GetMapping("/client/cart/updateCart/{id}/item/{itemId}/number/{number}")
    public String updateCart(@PathVariable("id") long id, @PathVariable("itemId") long itemId,
            @PathVariable("number") int number, @ModelAttribute("listCarts") Cart cart,
            Authentication authentication) {
        
        User user = getUser_(authentication);
        Cart extingCart = cartService.getCartById(id);
        extingCart.setId(cart.getId());
        extingCart.setNumber(number);
        extingCart.setUser(user);
        cartService.updateCart(extingCart);

        return "redirect:/client/cart";
    }
}
