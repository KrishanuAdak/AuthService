package com.example.demo1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		AuthDB auth=null;
		auth=this.repo.findByEmail(username);
		if(auth==null) {
			throw new UsernameNotFoundException("Not found");
		}
		return User.builder().username(auth.getEmail()).password(auth.getPassword()).roles(auth.getRole()).build();
	}
	

}
