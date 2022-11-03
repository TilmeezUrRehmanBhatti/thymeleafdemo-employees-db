package com.tilmeez.springboot.thymeleafdemo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidEmail {

    // define default error messages
    public String message() default "is required";

    // define default group
    public Class<?>[] groups() default {};

    // define default payload
    public Class<? extends Payload> [] payload() default {};
}
