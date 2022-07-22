package com.mywebsite.webbanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mywebsite.webbanhang.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
