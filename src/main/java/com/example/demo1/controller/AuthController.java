package com.example.demo1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo1.model.AuthDB;
import com.example.demo1.service.AuthService;
import java.util.*;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@RestController
@RequestMapping("/auth-service")
public class AuthController {
	@Autowired
	private AuthService service;
	
	@PostMapping("/register")
	public ResponseEntity<?> saveDetails(@RequestBody AuthDB auth) {
		AuthDB data=this.service.saveAuthDetails(auth);
		if(data!=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(data);
		}
		return ResponseEntity.badRequest().body("Registration Failed! Please try again!!");

			
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthDB auth) {
		String token=this.service.login(auth);
		Map<String,Object> response=new HashMap<>();
		response.put("Token", token);
		response.put("Status", HttpStatus.OK.value());
		if(token.length()>0 && token.startsWith("ey")) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token Login  failed!!");
			
	}
	
	@GetMapping("/patient/{id}")
	public String getEmailById(@PathVariable("id") int patient_id) {
		return this.service.getEmail(patient_id);
	}
	

}
