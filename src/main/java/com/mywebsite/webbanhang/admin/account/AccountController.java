package com.mywebsite.webbanhang.admin.account;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywebsite.webbanhang.login_register.model.User;
import com.mywebsite.webbanhang.login_register.service.RoleService;
import com.mywebsite.webbanhang.login_register.service.UserService;

@Controller
public class AccountController {
    UserService userService;
    RoleService roleService;

    public AccountController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/account")
    public String viewAccountPage(Model model) {
        return findPaginated(1, "id", "asc", model);
    }

    @GetMapping("/admin/account/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDir") String sortDir,
            Model model) {
        int pageSize = 6;

        Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<User> listUsers = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUsers", listUsers);
        return "/admin/account_/account";
    }

    @GetMapping("/admin/deleteAccount/{id}")
    public String deleteAccount(@PathVariable long id){
        userService.deleteAccountById(id);
        return "redirect:/admin/account";
    }

    @GetMapping("/admin/showUpdateAccount/{id}")
    public String showUpdateAccount(@PathVariable long id, Model model){
        model.addAttribute("user", userService.getAccountById(id));
        model.addAttribute("listRoles", roleService.listAllRole());
        return "/admin/account_/accountUpdate";
    }

    @PostMapping("/admin/updateAccount/{id}")
    public String updateAccount(@PathVariable long id, @ModelAttribute("user") User user) {
        User extingUser = userService.getAccountById(id);
        extingUser.setId(user.getId());
        extingUser.setFirstname(user.getFirstname());
        extingUser.setLastname(user.getLastname());
        extingUser.setEmail(user.getEmail());
        extingUser.setRoles(user.getRoles());

        userService.updateAccountById(extingUser);
        return "redirect:/admin/account";
    }
}
