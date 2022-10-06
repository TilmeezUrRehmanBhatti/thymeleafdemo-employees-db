package com.tilmeez.springboot.thymeleafdemo.controller;

import com.tilmeez.springboot.thymeleafdemo.entity.Employee;
import com.tilmeez.springboot.thymeleafdemo.service.EmployeeService;
import com.tilmeez.springboot.thymeleafdemo.user.ErmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private  EmployeeService employeeService;


    private Logger logger = Logger.getLogger(getClass().getName());


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model thModel) {

        thModel.addAttribute("ermUser", new ErmUser());

        return "authentication/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("ermUser") ErmUser theErmUser,
            BindingResult theBindingResult,
            Model theModel) {

        String userName = theErmUser.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()){
            return "authentication/registration-form";
        }

        // check the database if user already existed
        Employee existing = employeeService.findByUserName(userName);

        if (existing != null) {
            theModel.addAttribute("ermUser", new ErmUser());
            theModel.addAttribute("registrationError", "User name already exists");

            logger.warning("User name already exists");

            return "authentication/registration-form";
        }

        // save user in database
        employeeService.save(theErmUser);

        logger.info("Successfully create user: " + userName);

        return "authentication/registration-confirmation";
    }


}
