package com.user_auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

@Service
public class JwtService {

    private static final Logger logger= LoggerFactory.getLogger(JwtService.class);

    private String key;

    public JwtService(@Value("${jwt.secret}") String key){

        //Commented all this code for getting manual key that can be shared
//        try{
//            logger.info("Initializing the key...");
//
//            KeyGenerator keyGenerator=KeyGenerator.getInstance("HMACSHA256");
//            SecretKey secretKey=keyGenerator.generateKey();
//
//            this.key= Base64.getEncoder().encodeToString(secretKey.getEncoded());
//
//            logger.info("Key successfully initialized...");
//        }catch (Exception e){
//            logger.info("Error occurred while initializing the keys");
//            throw new RuntimeException(e);
//        }
        this.key=key;
        logger.info("Key is -->"+this.key);

    }

    public String generateJwtToken(String username, List<String> authorities,Integer userId) throws NoSuchAlgorithmException {

        logger.info("Generating the token...");
        Map<String,Object> claims=new HashMap<>();
        claims.put("Authorities",authorities);
        claims.put("UserId",userId);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis( 3600000)))
                .and()
                .signWith(this.getKey())
                .compact();
    }

    public SecretKey getKey(){
        return new SecretKeySpec(this.key.getBytes(StandardCharsets.UTF_8),"HmacSHA256");
    }

    public Claims getAllClaims(String jwt){

        logger.info("Getting all claims.....");
        return Jwts.parser()
                .verifyWith(this.getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public String getUsername(String jwt){

        return this.getAllClaims(jwt).getSubject();
    }


    public boolean isExpired(String jwt){

        return this.getAllClaims(jwt).getExpiration().before(new Date());
    }

    public boolean isValidToken(String jwt){

        try{
            return !isExpired(jwt);
        }
        catch (Exception e){
            logger.info("Not a valid token",e);
            return false;
        }

    }

}
