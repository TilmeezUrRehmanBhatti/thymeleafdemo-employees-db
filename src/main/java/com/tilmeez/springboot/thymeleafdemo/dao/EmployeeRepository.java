package com.tilmeez.springboot.thymeleafdemo.dao;

import com.tilmeez.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
