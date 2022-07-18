package com.mywebsite.webbanhang.login_register.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mywebsite.webbanhang.login_register.model.User;
import com.mywebsite.webbanhang.login_register.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
    User save(UserRegistrationDto registrationDto, long id);
    List<User> listAllUser();
    void deleteAccountById(long id);
    User getAccountById(long id);
    User updateAccountById(User user);
    Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
    User getUserByEmail(String email);
}
