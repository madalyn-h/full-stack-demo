package com.harger.flightreservation.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.harger.flightreservation.entities.User;
import com.harger.flightreservation.repos.UserRepository;
import com.harger.flightreservation.services.SecurityService;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private SecurityService securityService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@RequestMapping("/showReg")
	public String showRegistrationPage() {
		LOGGER.info("Inside showRegistrationPage()");
		return "login/registerUser";
	}
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String register(@ModelAttribute("user") User user) {
		LOGGER.info("Inside register()" + user);
		user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
		return "login/login";
	}
	
	@RequestMapping("/showLogin")
	public String showLoginPage() {
		LOGGER.info("Inside showLoginPage()");
		return "login/login";
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap modelMap) {
		boolean loginResponse = securityService.login(email, password);
		LOGGER.info("Inside login() email: " + email);
		loginResponse = securityService.login(email, password);
		if(loginResponse == true) {
			return "findFlights";
		} else {
			modelMap.addAttribute("msg", "Invalid Username Or Password. Please try again");
		}
		return "login/login";
	}
}
