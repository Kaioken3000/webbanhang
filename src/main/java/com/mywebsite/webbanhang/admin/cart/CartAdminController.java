package com.mywebsite.webbanhang.admin.cart;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywebsite.webbanhang.client.cart.Cart;
import com.mywebsite.webbanhang.client.cart.CartService;

@Controller
public class CartAdminController {
    CartService cartService;

    public CartAdminController(CartService cartService) {
        this.cartService = cartService;
    }

    //show page
    @GetMapping("/admin/cart")
    public String viewCartPage(Model model) {
        return findPaginated(1, "id", "asc", model);
    }

    @GetMapping("/admin/cart/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {
        int pageSize = 6;

        Page<Cart> page = cartService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Cart> listCarts = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCarts", listCarts);
        Cart cart = new Cart();
        model.addAttribute("cart", cart);

        return "/admin/cart_/cartAdmin";
    }


    // Cart detail
    @GetMapping("/admin/showCartDetail/{id}")
    public String showCartDetailPage(@PathVariable long id, Model model) {
        model.addAttribute("listCartsByUser", cartService.listCartByUserLoginId(id));
        return "/admin/cart_/cartDetail";
    }
    
}
