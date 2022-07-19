package com.mywebsite.webbanhang.admin.dashboard;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mywebsite.webbanhang.admin.category.Category;
import com.mywebsite.webbanhang.admin.category.CategoryService;
import com.mywebsite.webbanhang.admin.item.Item;
import com.mywebsite.webbanhang.admin.item.ItemService;
import com.mywebsite.webbanhang.client.cart.Cart;
import com.mywebsite.webbanhang.client.cart.CartService;
import com.mywebsite.webbanhang.login_register.model.Role;
import com.mywebsite.webbanhang.login_register.model.User;
import com.mywebsite.webbanhang.login_register.service.UserService;

@Controller
public class DashboardController {

    UserService userService;
    CartService cartService;
    ItemService itemService;
    CategoryService categoryService;

    public DashboardController(UserService userService, CartService cartService, ItemService itemService,
            CategoryService categoryService) {
        this.userService = userService;
        this.cartService = cartService;
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/dashboard")
    public String showAdminPage(Model model) {
        // Userdata 
        List<User> userData = userService.listAllUser();
        Iterator<User> itrUser = userData.iterator();
        int roleUserNumber=0;
        int roleAdminNumber=0;
        while(itrUser.hasNext()){
            User user = (User) itrUser.next();
            Set<Role> roles = user.getRoles();
            Iterator<Role> itrRole = roles.iterator();
            while(itrRole.hasNext()){
                Role role = (Role) itrRole.next();
                if(role.getName().equals("ROLE_USER"))
                    roleUserNumber+=1;
                roleAdminNumber+=1;
            }
        }
        
        Map<String, Integer> accountUserChartData = new TreeMap<>();
        accountUserChartData.put("USER", roleUserNumber);
        accountUserChartData.put("ADMIN", roleAdminNumber);
        model.addAttribute("chartDataUser", accountUserChartData);

        
        // Category data
        List<Cart> cartList = cartService.listAllCart();
        Iterator<Cart> itrCartList = cartList.iterator();

        List<User> userDataCart = userService.listAllUser();
        Iterator<User> itrUserDataCart = userDataCart.iterator();

        Map<String, Integer> cartChartData = new TreeMap<>();
        while(itrUserDataCart.hasNext()){
            User user = (User) itrUserDataCart.next();
            int numberItemCartOfUser=0;
            while(itrCartList.hasNext()){
                Cart cart = (Cart) itrCartList.next();
                if(user.getId() == cart.getUser().getId())
                    numberItemCartOfUser+=cart.getNumber();
            }
            cartChartData.put(user.getEmail(), numberItemCartOfUser);
        }
        model.addAttribute("chartDataCategory", cartChartData);


        //itemNumber
        List<Item> itemNum = itemService.listAllItem();
        model.addAttribute("itemNumber", itemNum.size());

        //categoryNumber
        List<Category> categoryNum = categoryService.listAllCategory();
        model.addAttribute("categoryNumber", categoryNum.size());
        
        //CartNumber
        List<Cart> cartNum = cartService.listAllCart();
        model.addAttribute("cartNumber", cartNum.size());
        
        //AccountNumber
        List<User> userNum = userService.listAllUser();
        model.addAttribute("userNumber", userNum.size());


        return "/admin/dashboard";
    }
}
