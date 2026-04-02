package com.user_auth.filter;

import com.user_auth.services.JwtService;
import com.user_auth.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger= LoggerFactory.getLogger(JwtFilter.class);

    JwtService jwtService;

    JwtFilter(JwtService jwtService){
        this.jwtService=jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("Getting jwt header");

        String authHeader=request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer")){
            logger.info("Jwt header not found...");
            filterChain.doFilter(request,response);
            return;
        }


        try {
            logger.info("Jwt token found in header...");
            String jwt=authHeader.substring(7);


            if(SecurityContextHolder.getContext().getAuthentication()!=null){
                filterChain.doFilter(request,response);
                return;
            }

            logger.info("Checking token...");

            if(!this.jwtService.isValidToken(jwt)) {
                logger.info("Its not a valid token...");
                filterChain.doFilter(request,response);
                return;
            }

            logger.info("Its a valid token...");

            List<String> authorities=this.jwtService.getAllClaims(jwt)
                    .get("Authorities",List.class);

            List<SimpleGrantedAuthority> grantedAuthorities=authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    =new UsernamePasswordAuthenticationToken(
                            this.jwtService.getUsername(jwt)
                            ,null,
                            grantedAuthorities
                            );

            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.info("Authentication done for the token.....");

        }
        catch (Exception e){
            logger.info("Exception occured..",e.getMessage());

        }
        filterChain.doFilter(request,response);
    }
}
