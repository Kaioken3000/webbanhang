package com.mywebsite.webbanhang.client.cart;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.mywebsite.webbanhang.login_register.model.User;
import com.mywebsite.webbanhang.login_register.service.UserService;

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

    @GetMapping("/client/cart")
    public String showCartPage(Model model){
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("listCarts", cartService.listCartByUserLoginId(user.getId()));
        return "/client/cart";
    }

    @PostMapping("/client/cart/addCart/{itemCart}")
    public RedirectView addCart(@PathVariable long itemCart){
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByEmail(principal.getName());
        
        Cart cart = new Cart();
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
    public String deleteAllUserCart(){
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByEmail(principal.getName());

        cartService.deleteAllCartByUserId(user.getId());
        return "redirect:/client/cart";
    }

    @GetMapping("/client/cart/updateCart/{id}/item/{itemId}/number/{number}")
    public String updateCart(@PathVariable("id") long id, @PathVariable("itemId") long itemId, @PathVariable("number") int number,  @ModelAttribute("listCarts") Cart cart ){
        Cart extingCart = cartService.getCartById(id);
        extingCart.setId(cart.getId());
        extingCart.setNumber(number);

        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByEmail(principal.getName());
        Cart c = cartService.getCartByItemIdAndUserId(user.getId(), itemId);
        extingCart.setItemCart(c.getItemCart());
        extingCart.setUser(user);

        cartService.updateCart(extingCart);

        return "redirect:/client/cart";
    }
}
