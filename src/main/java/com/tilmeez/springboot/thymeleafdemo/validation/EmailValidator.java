package com.tilmeez.springboot.thymeleafdemo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail,String> {

    private Pattern pattern;

    private Matcher matcher;

    // Email Regex – Strict Validation
    private static final String EMAIL_PATTERN =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext constraintValidatorContext) {

        pattern = Pattern.compile(EMAIL_PATTERN);
        if (email == null) {

            return false;
        }

        matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
