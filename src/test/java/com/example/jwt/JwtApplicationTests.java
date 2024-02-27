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
	void tokenCreate(){  // 토큰 생성 : 유저정보, 만료일시
		var claims = new HashMap<String, Object>();
		claims.put("user_id", 923);

		var expireAt = LocalDateTime.now().plusMinutes(30);
		//10분 동안만 유효

		var jwtToken = jwtService.create(claims, expireAt); //토큰생성
		System.out.println(jwtToken);
	}

	@Test
	void tokenValidation(){
		var token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo5MjMsImV4cCI6MTcwOTA2MTI2MX0.tTQzz8hrZgFctMIhPSmmKTrHlrbRaw_eHjfMQfHYQVY";
		jwtService.validation(token);

	}
}
