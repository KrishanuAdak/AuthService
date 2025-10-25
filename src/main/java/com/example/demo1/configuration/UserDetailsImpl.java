package com.example.demo1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo1.model.AuthDB;
import com.example.demo1.repo.AuthRepo;
@Service
public class UserDetailsImpl implements UserDetailsService{
	@Autowired
	private AuthRepo repo;
	
	
//	@Autowired(required=false)
//	private RedisTemplate<String,AuthDB> template;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		AuthDB auth=null;
		auth=this.repo.findByEmail(username);
		
//		auth=(AuthDB) template.opsForValue().get(username);
//		                                         registered-email
		System.out.println("Found Email on Redis: "+auth.getEmail());
		if(auth==null){
			throw new UsernameNotFoundException("User not found");
		}
	
//		else {
//		
//		return User.builder().username(auth.getEmail()).password(auth.getPassword()).roles(auth.getRole()).build();
		
		return User.builder().username(auth.getEmail()) .password(auth.getPassword()).roles(auth.getRole()).build();
//		}a
		
	}
	

}
