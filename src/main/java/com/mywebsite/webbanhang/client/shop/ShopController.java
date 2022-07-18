package com.mywebsite.webbanhang.client.shop;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywebsite.webbanhang.admin.category.CategoryService;
import com.mywebsite.webbanhang.admin.item.Item;
import com.mywebsite.webbanhang.admin.item.ItemService;
import com.mywebsite.webbanhang.client.cart.Cart;
import com.mywebsite.webbanhang.client.cart.CartService;
import com.mywebsite.webbanhang.login_register.model.User;
import com.mywebsite.webbanhang.login_register.service.UserService;

@Controller
public class ShopController {

    ItemService itemService;
    CategoryService categoryService;
    CartService cartService;
    HttpServletRequest request;
    UserService userService;

    public ShopController(ItemService itemService, CategoryService categoryService, CartService cartService,
            HttpServletRequest request, UserService userService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/client/shop")
    public String viewShopPage(Model model) {
        return findPaginated(1, "id", "asc", model);
    }

    @GetMapping("/client/shop/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {
        int pageSize = 6;

        Page<Item> page = itemService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Item> listItems = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        // show page
        model.addAttribute("itemLists", listItems);
        model.addAttribute("category_list", categoryService.listAllCategory());

        try {
            Principal principal = request.getUserPrincipal();
            User user = userService.getUserByEmail(principal.getName());
            model.addAttribute("listCarts", cartService.listCartByUserLoginId(user.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Cart cart = new Cart();
        model.addAttribute("cart", cart);
        return "/client/shop";
    }

    @GetMapping("/client/checkout")
    public String showCheckPage(Model model) {
        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("listCarts", cartService.listCartByUserLoginId(user.getId()));
        return "/client/checkout";
    }
}
