package com.user_auth.configuration;

import com.user_auth.filter.JwtFilter;
import com.user_auth.services.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter) throws Exception{

        httpSecurity.csrf(customizer->customizer.disable())
        .authorizeHttpRequests(request->
                request.requestMatchers("/signup","/login")
                                .permitAll()
                        .anyRequest()
                .authenticated());

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.sessionManagement(session->session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        ));
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(MyUserDetailsService myUserDetailsService){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(myUserDetailsService);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
        provider.setPasswordEncoder(encoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){

        return configuration.getAuthenticationManager();
    }
}
