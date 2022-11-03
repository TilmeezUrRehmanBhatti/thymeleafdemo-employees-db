package com.tilmeez.springboot.thymeleafdemo.dao;

import com.tilmeez.springboot.thymeleafdemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findRoleByName(String roleName);
}
