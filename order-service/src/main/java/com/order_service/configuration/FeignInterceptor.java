package com.order_service.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class FeignInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor(){

        return requestTemplate -> {
            //it's necessary to write it here otherwise it will not be able to get the security context
            Jwt jwt= ((JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication()).getToken();
            requestTemplate.header("Authorization","Bearer "+jwt.getTokenValue());
        };
    }
}
