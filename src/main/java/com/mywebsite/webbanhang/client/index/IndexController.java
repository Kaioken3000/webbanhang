package com.mywebsite.webbanhang.client.index;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mywebsite.webbanhang.admin.category.CategoryService;
import com.mywebsite.webbanhang.admin.item.Item;
import com.mywebsite.webbanhang.admin.item.ItemService;
import com.mywebsite.webbanhang.client.cart.Cart;
import com.mywebsite.webbanhang.client.cart.CartService;
import com.mywebsite.webbanhang.login_register.model.User;
import com.mywebsite.webbanhang.login_register.service.UserService;

@Controller
public class IndexController {

    ItemService itemService;
    CategoryService categoryService;
    CartService cartService;
    HttpServletRequest request;
    UserService userService;

    public IndexController(ItemService itemService, CategoryService categoryService, CartService cartService,
            HttpServletRequest request, UserService userService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.request = request;
        this.userService = userService;
    }

    @GetMapping("/client/index")
    public String showIndexPage(Model model) {
        int pageSize = 6;
        Page<Item> page = itemService.findPaginated(1, pageSize, "id", "asc");
        List<Item> listItems = page.getContent();
        // model.addAttribute("itemLists", itemService.listAllItem());
        model.addAttribute("itemLists", listItems);
        model.addAttribute("category_list", categoryService.listAllCategory());

        Principal principal = request.getUserPrincipal();
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("listCarts", cartService.listCartByUserLoginId(user.getId()));

        Cart cart = new Cart();
        model.addAttribute("cart", cart);
        return "/client/index";
    }

    @GetMapping("/")
    public String showIndexPage_(Model model) {
        int pageSize = 6;
        Page<Item> page = itemService.findPaginated(1, pageSize, "id", "asc");
        List<Item> listItems = page.getContent();
        // model.addAttribute("itemLists", itemService.listAllItem());
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
        return "/client/index";
    }

}
