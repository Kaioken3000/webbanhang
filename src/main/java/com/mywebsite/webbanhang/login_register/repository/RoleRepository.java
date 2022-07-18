package com.mywebsite.webbanhang.login_register.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mywebsite.webbanhang.login_register.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
