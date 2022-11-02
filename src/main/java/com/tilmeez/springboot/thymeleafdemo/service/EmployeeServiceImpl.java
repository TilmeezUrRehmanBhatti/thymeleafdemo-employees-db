package com.tilmeez.springboot.thymeleafdemo.service;


import com.tilmeez.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.tilmeez.springboot.thymeleafdemo.dao.RoleRepository;
import com.tilmeez.springboot.thymeleafdemo.entity.Employee;
import com.tilmeez.springboot.thymeleafdemo.entity.Role;
import com.tilmeez.springboot.thymeleafdemo.user.ErmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private Logger logger = Logger.getLogger(getClass().getName());
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepository.findById(theId);

        Employee theEmployee = null;

        if (result.isPresent()) {
            theEmployee = result.get();
        }else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + theId);
        }
        return theEmployee;
    }


    @Override
    public void save(Employee theEmployee) {
        employeeRepository.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }

    @Override
    public List<Employee> searchBy(String theName) {

        List<Employee> results = null;

        if (theName != null && (theName.trim().length() > 0)) {
            results = employeeRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(theName, theName);
        }else {
            results = findAll();
        }
        return results;
    }

    @Override
    public void save(ErmUser ermUser) {

        Employee employee = new Employee();

        // assign user details to the user object
        employee.setUserName(ermUser.getUserName());
        employee.setPassword(passwordEncoder.encode(ermUser.getPassword()));
        employee.setFirstName(ermUser.getFirstName());
        employee.setLastName(ermUser.getLastName());
        employee.setEmail(ermUser.getEmail());

        // give user role of "EMPLOYEE"
        employee.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_EMPLOYEE")));

        // save the user in database
        employeeRepository.save(employee);
    }

    @Override
    public Employee findByUserName(String userName) {
        // check the database if the user already exists
        return employeeRepository.findByUserName(userName);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByUserName(username);

        if (username == null) {
            throw new UsernameNotFoundException("Invalid user name or password");
        }
        System.out.println(employee.getUserName());


        return new User(employee.getUserName(),
                employee.getPassword(),
                mapRolesToAuthorities(employee.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(
                        role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
