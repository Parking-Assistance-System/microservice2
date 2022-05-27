package com.vishal.login.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


import javax.crypto.SecretKey;

@Service
public class JwtUtil {
	
	  @Value("${jwt.signing.key}")
	    private String signingKey;

	 public String generateJwt(String username){

	     
	        // claims
	        Claims claims = Jwts.claims();
	                
	        // optional claims
	        claims.put("username", username);
	        
	        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
	        // generate jwt using claims
	        return Jwts.builder()
	                .setClaims(claims)
	                .signWith(key)
	                .compact();
	    }
	 public Claims verify(String jwt)  {

	       

		 
		 SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
		 
	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(jwt)
	                .getBody();
	        return claims;
		 
		
		
	           
	       

	    }

}
