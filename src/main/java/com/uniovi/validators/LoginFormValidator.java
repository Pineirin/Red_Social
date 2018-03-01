package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.*;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Component
public class LoginFormValidator implements Validator{
	@Autowired
	private UsersService usersService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		
		User userEnBD=usersService.getUserByEmail(user.getEmail());
		
		if (user.getEmail().length() < 5 || user.getEmail().length() > 24) {
			errors.rejectValue("email", "Error.signup.email.length");
		}
		if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
		}
		if (user.getPassword() != userEnBD.getPassword()) {
			errors.rejectValue("password", "Error.signup.password.length");
		}
	}
}
