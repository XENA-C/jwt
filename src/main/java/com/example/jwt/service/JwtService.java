package com.example.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
            LocalDateTime expireAt      //만료일자
    ){
        var key = Keys.hmacShaKeyFor(secretKey.getBytes()); //암호화
        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());  //->>LocalDateTime -> Date 타입으로

        return Jwts.builder() //JsonWebToken
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setExpiration(_expireAt)
                .compact(); //String 타입 리턴
    }

    //토큰 검증
    public void validation(String token){

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var parser = Jwts.parserBuilder()  //Parser 생성
                .setSigningKey(key)
                .build();

      try {
            var result = parser.parseClaimsJws(token);

            //get Header, Body, Signature
            result.getBody().entrySet().forEach(value -> { //body 의 데이터 출력
                log.info("key:{}, value:{}", value.getKey(), value.getValue());
                //{중괄호} 기준으로 값 입력
            });

        }catch (Exception e){ //토큰 사인 에러
            if (e instanceof SignatureException){
                    throw new RuntimeException("Invalid JWT Token Signature Exception");

            }else if(e instanceof ExpiredJwtException){ //토큰 만료 에러
                throw new RuntimeException("Expired JWT Token Exception");

            }else {
                throw  new JwtException("JWT Token Validation Exception");
            }
        }

    }


    }


