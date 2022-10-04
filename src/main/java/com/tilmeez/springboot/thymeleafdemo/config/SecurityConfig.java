package com.tilmeez.springboot.thymeleafdemo.config;

import com.tilmeez.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final DataSource securityDataSource;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public SecurityConfig(@Qualifier("securityDataSource") DataSource securityDataSource) {
        this.securityDataSource = securityDataSource;
    }


    @Bean
    public UserDetailsManager userDetailsManager() {
        return new JdbcUserDetailsManager(securityDataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http
                .authorizeRequests(configure ->
                        configure
                                .antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
                                .antMatchers("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
                                .antMatchers("/employees/delete").hasRole("ADMIN")
                                .antMatchers("/employees/**").hasRole("EMPLOYEE")
                                .antMatchers("/resources/**").permitAll())
                .formLogin(configure ->
                        configure
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(configure ->
                        configure
                                .accessDeniedPage("/access-denied"))
                .build();
    }

    // bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

        //set the custom employee details service
        auth.setUserDetailsService(employeeService);

        // set th password encoder
        auth.setPasswordEncoder(passwordEncoder());

        return auth;
    }


}

