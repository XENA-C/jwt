package com.example.jwt;

import com.example.jwt.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.HashMap;

@SpringBootTest
class JwtApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
	}

	@Test
	void tokenCreate(){
		var claims = new HashMap<String, Object>();
		claims.put("user_id", 923);

		var expireAt = LocalDateTime.now().plusMinutes(30);
		//10분 동안만 유효

		var jwtToken = jwtService.create(claims, expireAt);

		System.out.println(jwtToken);

	}

	@Test
	void tokenValidation(String token){


	}
}
