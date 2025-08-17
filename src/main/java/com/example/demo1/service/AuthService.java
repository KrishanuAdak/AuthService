package com.example.demo1.service;



import org.springframework.stereotype.Service;

import com.example.demo1.model.AuthDB;
import com.example.demo1.repo.AuthRepo;
import com.example.demo1.util.JwtUtil;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthService {
	@Autowired
	private AuthRepo repo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired 
	private AuthenticationManager authManager;
	
	public AuthDB  saveAuthDetails(AuthDB auth) {
		AuthDB data=auth;
		data.setEmail(auth.getEmail());
		data.setPassword(encoder.encode(auth.getPassword()));
		data.setRole(auth.getRole());
		AuthDB newData=this.repo.save(data);
//		String token=this.jwtUtil.createToken(auth);
		return newData;
		
	}
	
	public String getEmail(int patient_id) {
		return this.repo.getEmailByPatientId(patient_id);
	}
	public String login(AuthDB auth) {
	
	    authManager.authenticate(
	            new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));
	   AuthDB data=this.repo.findByEmail(auth.getEmail());
	   String token = jwtUtil.generateToken(data);
	   return token;
	    
	}
	

}
