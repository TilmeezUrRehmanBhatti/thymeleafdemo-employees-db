**Big Picture**

<img src="https://user-images.githubusercontent.com/80107049/193446782-61782be6-a616-49e5-919e-0dd4cec948f0.png" width="500" />



**Application Architecture**

<img src="https://user-images.githubusercontent.com/80107049/193446797-d0639d8b-8ecb-4a81-b7ec-ebbc4bae3174.png" width="500" />


**Add Employee**

1. New **Add Employee** button for list-employees.html
2. Create HTML form for new employee
3. Process form data to save employee

_Step 1:New "Add Employee" button_

+ **Add Employee** button will href link to
    + request mapping **/employees/showFormForAdd**

```HTML
<a th:href="@{/employees/showFormForAdd}"
   class="btn btn-primary btn-sm mb-3">
  Add Employee
</a>
```

+ `class="btn btn-primary btn-sm mb-3"`
    + btn = Button
    + btn-primary = Button Primary
    + btn-sm = Button Small
    + mb-3 = Margin Bottom, 3 pixels


**Showing Form**

In our Spring Controller

+ Before we show the form, we must add a **model attribute**
+ This is an object that will hold form data for the **data binding**

**Controller code to show form**

```JAVA
@Controller
@RequestMapping("/employees")
public class EmployeeController {
  
  @GetMapping("/showFormForAdd")
  public String showFormForAdd(Model theModel) {
    
    // create model attribute to bind form data
    Employee theEmployee = new Employee();
    
    theModel.addAttrivute("employee", theEmployee);
    
    return "employees/employee-form";
  }
}
```
+ `theModel.addAttrivute("employee", theEmployee);` Our Thymeleaf template will access this data for binding form data


**Thymeleaf and Spring MVC Data Binding**

+ Thymeleaf has special expressions for binding Spring MVC fprm data
+ Automatically setting / retrieving data from a Java object

**Thymeleaf Expression**

+ Thymeleaf expressions can help us build the HTML form

| Expression | Description                                                                      |
| ---------- | -------------------------------------------------------------------------------- |
| th:action  | Location to send form data                                                       |
| th:object  | Reference to model attribute                                                     |
| th:field   | Bind input field to a property on model attribute                                |
| more ...   | https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html#creating-a-form |


_Step 2:Create HTML form for new employee_

```HTML
<form action="#" th:action="@/employees/save}"
      th:object="${employee}" method="POST">
  
</form>
```
+ `action="#"` # is Empty place holder Thymeleaf will handle real work
+ `th:action="@/employees/save}` Real work, Send form data to /employees/save

```HTML
<form action="#" th:action="@/employees/save}"
      th:object="${employee}" method="POST">
  
  <imput type="text" th:field="*{firstName}" placeholder="First name" />
    
  <imput type="text" th:field="*{lastName}" placeholder="last name"/>
  
  <imput type="text" th:field="*{email}" placeholder="Email"/>
  
  <button type="submit">Save</button>
  
</form>
```

+ `th:field="*{firstName}"` \*{...} Selects property on referenced th:object `th:object="${employee}"`

<br>

+ When form is **loaded** will call
    + employee.getFirstName()
    + ...
    + employee.getLastName()

+ Call getter methods to populate form fields initially

<br>

+ When form is **submitted** will call:
    + employee.setFIrstName(...)
    + ...
    + employee.setLastName(...)

+ Call setter methods to populate Java object with form data

**Apply Bootstrap styles**


```HTML
<form action="#" th:action="@/employees/save}"
      th:object="${employee}" method="POST">
  
  <imput type="text" th:field="*{firstName}" placeholder="First name"
         class="form-controller mb-4 col-4"/>
    
  <imput type="text" th:field="*{lastName}" placeholder="Last name"
         class="form-controller mb-4 col-4"/>
  
  <imput type="text" th:field="*{email}" placeholder="Email"
         class="form-controller mb-4 col-4"/>
  
  <button type="submit" class="btn btn-info col-2">Save</button>
  
</form>
```

+ class="form-controller mb-4 col-4"
    + Form control
    + Margin Bottom, 4 pixels
    + Column Span 4

+ class="btn btn-info col-2"
    + Button
    + Button info
    + Column Span 2

_Step 3:Process form data to save employee_

```JAVA
@Controller
@RequestMapping("/employees")
public class EmployeeController {
  
...
     @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

        // save the employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }
 
}

```

+ Redirect to request mapping /employees/list
    + https://en.wikipedia.org/wiki/Post/Redirect/Get



**Update Employee**

1. "Update" Employee
2. Pre-populate the form
3. Process form data

_Step 1:"Update button_

+ Update button includes employee id

```HTML
<tr th:each="tempEmployee : ${employees}">
  ...
  <td>
    
    <a th:href="@{/employees/showFormForUpdate{employeeId=${tempEmployee.id}}}"
       class="btn btn-info btn-sm w-25">
      Update
    </a>
    
  </td>
  
</tr>
```


_Step 2:Pre-populate Form_

```JAVA
@Controller
@RequestMapping("/employees")
public class EmployeeControler {
  
  ...
  
  
      @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {

        // get the employee from the service
        Employee theEmployee = employeeService.findById(theId);

        // set employee as a model attribute to pre-populate the form
        theModel.addAttribute("employee", theEmployee);

        // send over to our form
        return "employees/employee-form";
    }
}  
```



```HTML
<form action="#" th:action="@/employees/save}"
      th:object="${employee}" method="POST">
  
  <!-- Add hiddent form field to handle update -->
  <input type="hidden" th:field="*{id}" />
  
  <imput type="text" th:field="*{firstName}" placeholder="First name"
         class="form-controller mb-4 col-4"/>
    
  <imput type="text" th:field="*{lastName}" placeholder="Last name"
         class="form-controller mb-4 col-4"/>
  
  <imput type="text" th:field="*{email}" placeholder="Email"
         class="form-controller mb-4 col-4"/>
  
  <button type="submit" class="btn btn-info col-2">Save</button>
  
</form>
```


****Update Employee****

_Step 1:"Delete" button_

+ **Delete button includes employee id**

```HTML
<tr th:each="tempEmployee :${emlpoyee=">
  
  ...
  <td>
    <a th:href="@{/employees/delete{employeeId=${tempEmployee.id}}}"
       class="btn btn-info btn-sm w-25"
       onclick="if (!(confirm('Are you want to delete this employee?'))) return false">
      Delete
    </a>
    
  </td>
</tr>
```


_Step 2:Add controller code for delete_

```JAVA
@Controller
@RequestMapping("/employees")
public class EmployeeController {
  
  @GetMapping
  public String delete(@RequestParam("employeeId") int theId) {
    
    // delete the employees
    employeesService.deleteById(theId);
    
    // redirect to /employees/list
    return "redirect:/employees/list;
    }
  ...
}
```



### SPRING BOOT, SPRING SECURITY AND THYMELEAF


| user id        | password           | roles  |
| ------------- |:-------------:| :-----|
| john   | test123 | ROLE_EMPLOYEE   | 
| mary      | test123      |  ROLE_EMPLOYEE, ROLE_MANAGER|
| susan | test123      |  ROLE_EMPLOYEE, ROLE_ADMIN |

**MAVEN PROJECT UPDATES**

We need to add entries for Spring Security and Thymeleaf Security

1. Add Spring Security starter

   	<dependency>
   		<groupId>org.springframework.boot</groupId>
   		<artifactId>spring-boot-starter-security</artifactId>
   	</dependency>


2. Add the Thymeleaf pom entries for Spring Security

   	<dependency>
   		<groupId>org.thymeleaf.extras</groupId>
   		<artifactId>thymeleaf-extras-springsecurity5</artifactId>
   	</dependency>


CREATE BEANS FOR DATABASE ACCESS
================================


This Spring Boot project will make use of two different datasources
1. Main datasource for the app. This is for accessing the "employee" database
2. Another datasource for the application security. This is for accessing the security info database.

3. The database configs are in the file: application.properties

For the main application, these are the database connection properties


4. The datasources are configured in the file: DataSourceConfig.java
```JAVA
@Configuration
@EnableJpaRepositories(basePackages={"${spring.data.jpa.repository.packages}"})
public class DataSourceConfig {
	
	@Primary
	@Bean
	@ConfigurationProperties(prefix="app.datasource")
	public DataSource appDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix="spring.data.jpa.entity")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource appDataSource) {
		return builder
				.dataSource(appDataSource)
				.build();
	}

	@Bean
	@ConfigurationProperties(prefix="security.datasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
}
```
Let's explain what's going on in this file.

a) Configure Spring Data JPA
`@EnableJpaRepositories(basePackages={"${spring.data.jpa.repository.packages}"})`

This tells the app that we are using JPA repositories defined in the given package. The package name is read from the application.properties file.

spring.data.jpa.repository.packages=com.tilmeez.springboot.thymeleafdemo.dao

In this case, the package name is: `com.tilmeez.springboot.thymeleafdemo.dao`, so Spring Data JPA will scan for JPA repositories in this package.   
Spring Data JPA makes use of a entity manager factory bean and transacation manager.
By default it will use a bean named, "entityManagerFactory". We manually configure this bean in this class.    
Also, by default, Spring Data JPA will use a bean named "transactionManager". The "transactionManager" bean is autoconfigured by Spring Boot.

b) Configure application DataSource
```JAVA
	@Primary
	@Bean
	@ConfigurationProperties(prefix="app.datasource")
	public DataSource appDataSource() {
		return DataSourceBuilder.create().build();
	}
```
This code creates a datasource. This datasource is for our main application data. It will read data "employee" data from the database. This datasource is configured with the following:

	`@ConfigurationProperties(prefix="app.datasource")`

The @ConfigurationProperties will read properties from the config file (application.properties). It will read the properties from the file with the prefix: "app.datasource". So it will read the following:

```properties
app.datasource.url=jdbc:postgresql://ec2-63-32-248-14.eu-west-1.compute.amazonaws.com/d9opamnj4amdcg
app.datasource.username=owwjcnzhxgdzwq
app.datasource.password=springstudent
```

c) Configure EntityManagerFactory
```JAVA
	@Bean
	@ConfigurationProperties(prefix="spring.data.jpa.entity")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource appDataSource) {
		return builder
				.dataSource(appDataSource)
				.build();
	}
```
The entity manager factory tells Spring Data JPA which packages to scan for JPA entities.
The @ConfigurationProperties will read properties from the config file (application.properties).
It will read the properties from the file with the prefix: "spring.data.jpa.entity". So it will read the following:

spring.data.jpa.entity.packages-to-scan=com.tilmeez.springboot.thymeleafdemo.entity


d) Configure Data Source for Security
```JAVA
	@Bean
	@ConfigurationProperties(prefix="security.datasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
```
Here we configure the datasource to access the security database. By default, Spring Security makes use of regular JDBC (no JPA). As a result, we only need a datasource so the configuration is a bit simpler.

The @ConfigurationProperties will read properties from the config file (application.properties). It will read the properties from the file with the prefix: "security.datasource". So it will read the following:
```properties
security.datasource.jdbc-url=jdbc:postgresql://ec2-63-32-248-14.eu-west-1.compute.amazonaws.com/d9opamnj4amdcg
security.datasource.username=springstudent
security.datasource.password=springstudent
```


CONFIGURE SPRING SECURITY FOR DATABASE AUTHENTICATION
=====================================================

Now that we have the Spring Security datasource set up, we need to use this datasource for authentication.

1. Update Spring Security Configuration

Update the file: DemoSecurityConfig.java to use this
```JAVA
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private  DataSource securityDataSource;
  
   // add a reference to our security data source

    @Autowired
    public SecurityConfig(DataSource securityDataSource) {
        this.securityDataSource = securityDataSource;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new JdbcUserDetailsManager(securityDataSource);
    }



```
This injects the "securityDataSource" bean that was defined in the previous file: DemoDataSourceConfig.java. Then in the configure() method, we tell Spring Security to use this data source for JDBC authentication.

The JdbcUserDetailsManager manages the users in a SQL database. It connects to the database directly through JDBC. This way, the JdbcUserDetailsManager is independent of any other framework or specification related to database connectivity.

![clipboard.png](https://user-images.githubusercontent.com/80107049/193453798-45fd75b0-1db0-4464-8587-2038d25ce71b.png)

**Why UserDetailsManager**

In default, Spring only needs to find out the user’s details by using username, Spring does not need to do some operation on the user. However some applications needs more operational stuff, such as changing password, update the existing user etc.. In that case you should use a UserDetailsManager which extends UserDetailsService

DISPLAY CONTENT BASED ON USER ROLE
==================================

In the application, we want to display content based on user role.

- Employee role: users in this role will only be allowed to list employees.
- Manager role: users in this role will be allowed to list, add and update employees.
- Admin role: users in this role will be allowed to list, add, update and delete employees.

These restrictions are currently in place with the code: DemoSecurityConfig.java
```JAVA
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .authorizeRequests(configure ->
                        configure
                        .antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
                        .antMatcher("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
                        .antMatcher("/employees/delete").hasRole("ADMIN")
                        .antMatchers("/employees/**").hasRole("EMPLOYEE")
                        .antMatchers("/resources/**").permitAll())
      .formLogin(configure ->
                 configure
                 .loginPage("/showMyLoginPage")
                 .loginProcessingUrl("/authenticateTheUser")
                 .permitAll())
      .logout(configure ->
              configure
              .permitAll())
      .exceptionalHandling(configure ->
                           configure
                           .accessDenidedPage("/access-denied"))
      .build();
	}
```
We also, want to hide/display the links on the view page. For example, if the user has only the "EMPLOYEE" role, then we should only display links available for "EMPLOYEE" role.
Links for "MANAGER" and "ADMIN" role should not be displayed for the "EMPLOYEE".

We can make use of Thymeleaf Security to handle this for us.


1. Add support for Thymeleaf Security
   To use the Thymeleaf Security, we need to add the following to the XML Namespace

File: list-employees.html
```HTML
<html lang="en" 
		xmlns:th="http://www.thymeleaf.org"
		xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5">
```
Note the reference for xmlns:sec


2. "Update" button
   Only display the "Update" button for users with role of MANAGER OR ADMIN
```XML
					<div sec:authorize="hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')">

						<!-- Add "update" button/link -->
						<a th:href="@{/employees/showFormForUpdate(employeeId=${tempEmployee.id})}"
						   class="btn btn-info btn-sm">
							Update
						</a>

					</div>					
```
3. "Delete" buton

Only display the "Delete" button for users with role of ADMIN
```XML
					<div sec:authorize="hasRole('ROLE_ADMIN')">  
					
						<!-- Add "delete" button/link -->					
						<a th:href="@{/employees/delete(employeeId=${tempEmployee.id})}"
						   class="btn btn-danger btn-sm"
						   onclick="if (!(confirm('Are you sure you want to delete this employee?'))) return false">
							Delete
						</a>

					</div>
```
Spring Security User Registration - Employee User Details
=========================================================


**Define a BCryptPasswordEncoder and DaoAuthenticationProvider beans**

In our security configuration file, SecurityConfig.java, we define a BcryptPasswordEncoder and DaoAuthenticationProvider beans. We assign the EmployeeService and PasswordEncoder to the DaoAuthenticationProvider.

```JAVA
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
```

We are assigning the custom user details and password encoder to the DaoAuthenticationProvider.

https://www.javadevjournal.com/spring/password-encoding-in-spring-security/					


Create a ERM User class
=======================
For our registration form, we are creating a user class with custom details for the ERM project. It will have the username, password, first name, last name and email. We are also adding annotations for validating the fields.

```JAVA
package com.tilmeez.springboot.thymeleafdemo.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password",second="matchingPassword", message="The Password field must be matched" )
})
public class ErmUser {

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String lastName;

    @ValidEmail
    @NotNull(message = "is required")
    @Size(min = 1,message = "is required")
    private String email;

    public ErmUser() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```
Create Employee User Details entity classes (Employee, Role)
=====================================================
Creating the Employee and Role entity classes (We can use any name for these entities)

```JAVA
package com.tilmeez.springboot.thymeleafdemo.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "employee")
public class Employee {

    // define fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    // define constructors

    public Employee() {
    }

    public Employee(String userName, String password, String firstName, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Employee(String userName, String password, String firstName, String lastName, String email, Collection<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    // define getter/setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    // define toString

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

```JAVA
package com.tilmeez.springboot.thymeleafdemo.entity;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
```