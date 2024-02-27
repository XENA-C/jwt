package com.example.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SignatureException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class JwtService {


    private static String secretKey = "java11SpringBootJWTTokenIssueMethod";

   //토큰 생성
    public String create(
            Map<String, Object> claims, //Key, Value
            LocalDateTime expireAt
    ){
        var key = Keys.hmacShaKeyFor(secretKey.getBytes()); //
        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setExpiration(_expireAt)
                .compact(); //String 타입 리턴
    }

    //토큰 검증
    public void validation(String token){

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        try {
            var result = parser.parseClaimsJws(token); //토큰파싱
            result.getBody().entrySet().forEach(value -> {
                log.info("key:{}, value:{}", value.getKey(), value.getValue());
                //{중괄호} 기준으로 값 입력
            });

        }catch (Exception e){
            if (e instanceof SignatureException){
                    throw new RuntimeException("JWT Token Not Value Exception");

            }else if(e instanceof ExpiredJwtException){
                throw new RuntimeException("JWT Token Not Value Exception");

            }else {
                throw  new RuntimeException("JWT Token Not Value Exception");
            }
        }

    }


    }


