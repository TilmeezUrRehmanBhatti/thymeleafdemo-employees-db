package com.tilmeez.springboot.thymeleafdemo.dao;


import com.tilmeez.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    // add a method to sort my last name
    public List<Employee> findAllByOrderByLastNameAsc();
}
