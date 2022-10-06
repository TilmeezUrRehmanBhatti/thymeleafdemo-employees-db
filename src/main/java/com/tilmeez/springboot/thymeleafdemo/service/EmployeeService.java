package com.tilmeez.springboot.thymeleafdemo.service;


import com.tilmeez.springboot.thymeleafdemo.entity.Employee;
import com.tilmeez.springboot.thymeleafdemo.user.ErmUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    public List<Employee> findAll();

    public Employee findById(int theId);

    public void save(Employee theEmployee);

    public void deleteById(int theId);

    public List<Employee> searchBy(String theName);

    public Employee findByUserName(String userName);

    public void save(ErmUser ermUser);
}
