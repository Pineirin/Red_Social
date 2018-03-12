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
		
		User userEnBD=usersService.getUserByEmail(user.getEmail());
		
		if (userEnBD != null) {//si existe
			
			boolean sonPasswordsIguales=usersService.passwordsIguales(user.getPassword(), userEnBD.getPassword());
			
			if(!sonPasswordsIguales) {//si no tienen la misma contraseña
				
				errors.rejectValue("password", "Error.login.password.coincidence");
			}
		}
		else {
			errors.rejectValue("email", "Error.login.email.notExist");
		}
	}
}
