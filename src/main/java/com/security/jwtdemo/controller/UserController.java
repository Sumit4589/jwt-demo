package com.security.jwtdemo.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.security.jwtdemo.model.User;
import com.security.jwtdemo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User> getAllUsers(HttpServletRequest request) {
		System.out.println(request.getUserPrincipal().getName());
		System.out.println(request.getRemoteUser());
		System.out.println(request.getUserPrincipal());
		return userService.getAllUsers();
	}
	
}
