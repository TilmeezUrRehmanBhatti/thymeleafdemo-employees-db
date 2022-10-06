package com.tilmeez.springboot.thymeleafdemo.controller;

import com.tilmeez.springboot.thymeleafdemo.entity.Employee;
import com.tilmeez.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    // add mapping for "/list"
    @GetMapping("/list")
    public String listEmployees(Model theModel) {

        // get employee from db
        List<Employee> theEmployee = employeeService.findAll();

        // add to the spring model
        theModel.addAttribute("employees", theEmployee);

        return "employees/list-employees";
    }

    // add mapping for save
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {

        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        // set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

        // save the employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {

        // delete the employee
        employeeService.deleteById(theId);

        // redirect to /employees/list
        return "redirect:/employees/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("employeeName") String theName, Model thModel) {

        List<Employee> theEmployees = employeeService.searchBy(theName);

        // add to the spring model
        thModel.addAttribute("employees", theEmployees);

        // send to /employees/list
        return "/employees/list-employees";
    }


}
