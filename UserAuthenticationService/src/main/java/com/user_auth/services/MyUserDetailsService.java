package com.user_auth.services;

import com.user_auth.entity.User;
import com.user_auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger= LoggerFactory.getLogger(MyUserDetailsService.class);
    UserRepository userRepository;

    MyUserDetailsService(UserRepository userRepository){
        logger.info("The user-service details object is created...");
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Getting user-details from database...");
        User user=this.userRepository.findByEmail(username);

        if(user==null){
            logger.info("User not found with the email");
            throw new UsernameNotFoundException("No user found with the given email");
        }
        logger.info("User-Details fetched...");
        return  new MyUserDetails(user);
    }
}
