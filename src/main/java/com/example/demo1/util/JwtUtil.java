package com.example.demo1.util;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.demo1.model.AuthDB;

import java.util.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final String SECRET_KEY="hfihsdfiuifh8098nofhfhfih@hoihfohhkoolvhhncvbkhihdifheioheoe0fe0ehiheurjhfhfksnbsdnsdfsijfhihfsdjhbfsigfsfifsfsff";
	public SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		
	}
	public String generateToken(AuthDB auth) {
		Map<String,Object> claims=new HashMap<>();
		claims.put("role", auth.getRole());
		claims.put("ID",auth.getId());	
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(auth.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
				.signWith(getSigningKey())
				.compact();
		
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
		
	}
	public String extractEmail(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	public Date TokenExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}
	public boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
	public boolean isTokenValidated(String token) {
		return !isTokenExpired(token);
	}
	
	

}
