package com.vishal.login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.login.entities.Otp;
import com.vishal.login.entities.User;

import com.vishal.login.services.UserService;

import javax.servlet.http.HttpServletResponse;
@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/add")
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return user.getFullname();
    }

    @PostMapping("/user/auth")
    public void auth(@RequestBody User user) {
        userService.auth(user);
    }
    
    @GetMapping("user/exist")
    public boolean userExist(@RequestParam String username) {
    	return userService.isUserExist(username);
    }
    @GetMapping("car/exist")
    public boolean carExist(@RequestParam String carNo) {
    	return userService.isCarExist(carNo);
    }
    

    @PostMapping("/otp/check")
    public void check(@RequestBody Otp otp, HttpServletResponse response) {
        if (userService.check(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginAndGenerateToken(@RequestParam String username,@RequestParam String password){
    	
    
    	String token= userService.auth(username,password);
    	System.out.println(username);
    	if(token==null) {
    		return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
    	}
    	else {
    	return new ResponseEntity<String>(token,HttpStatus.OK);
    	}
    }
    
    
}
