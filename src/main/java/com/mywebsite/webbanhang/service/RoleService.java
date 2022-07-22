package com.mywebsite.webbanhang.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mywebsite.webbanhang.model.Role;
import com.mywebsite.webbanhang.repository.RoleRepository;

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
