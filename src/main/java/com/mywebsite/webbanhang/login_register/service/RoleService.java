package com.mywebsite.webbanhang.login_register.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mywebsite.webbanhang.login_register.model.Role;
import com.mywebsite.webbanhang.login_register.repository.RoleRepository;

@Service
public class RoleService {

    RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> listAllRole() {
        return roleRepository.findAll();
    }

}
