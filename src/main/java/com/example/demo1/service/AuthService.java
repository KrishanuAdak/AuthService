package com.example.demo1.service;



import org.springframework.stereotype.Service;

import com.example.demo1.model.AuthDB;
import com.example.demo1.repo.AuthRepo;
import com.example.demo1.util.JwtUtil;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.cache.annotation.Cacheable;

@Service
public class AuthService {
	private static final Logger logger=LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private AuthRepo repo;
	
//	@Autowired(required=false)
//	private RedisTemplate<String,AuthDB> redisTemplate;
//
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired 
	private AuthenticationManager authManager;
	
	
//	@Cacheable
	public AuthDB  saveAuthDetails(AuthDB auth) {
		AuthDB data=new AuthDB();
		data.setEmail(auth.getEmail());
//		logger.info("Email registered on redis"+redisTemplate.opsForValue().get("registered-email"));
		data.setPassword(encoder.encode(auth.getPassword()));
		data.setRole(auth.getRole());
		AuthDB newData=this.repo.save(data); 
//		/redisTemplate.opsForValue().set(auth.getEmail(), newData);
		return newData;
		
	}
	
	public String getEmail(int patient_id) {
		return this.repo.getEmailByPatientId(patient_id);
	}
	public String login(AuthDB auth) {
	
	    authManager.authenticate(
	            new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));
//	    redisTemplate.opsForValue().set("Email", auth.getEmail(),1,TimeUnit.DAYS);
//	    redisTemplate.opsForValue().set("password", encoder.encode(auth.getPassword()),1,TimeUnit.DAYS);
//	    String redis_email=(String) redisTemplate.opsForValue().get("Email"); 
//	    String redis_password= (String) redisTemplate.opsForValue().get("password");
//	    System.out.println("Redis from template : "+redis_email+" "+redis_password);
	    
	    AuthDB data=this.repo.findByEmail(auth.getEmail());
	    String token = jwtUtil.generateToken(data);
	    return token;
	    
	}
	

}
