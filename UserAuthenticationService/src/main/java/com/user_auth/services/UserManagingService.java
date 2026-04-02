package com.user_auth.services;

import com.user_auth.dto.UserDTO;
import com.user_auth.entity.Role;
import com.user_auth.entity.User;
import com.user_auth.exceptions.EmailAlreadyExistsException;
import com.user_auth.mapper.UserMapper;
import com.user_auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagingService {

    private static final Logger logger= LoggerFactory.getLogger(UserManagingService.class);

    UserRepository userRepository;

    UserManagingService(UserRepository userRepository){
        logger.info("UserManagingService is created....");
        this.userRepository=userRepository;
    }

    public UserDTO saveUser(UserDTO userDTO,String provider){

        logger.info("Saving the user....");
        User test= this.userRepository.findByEmail(userDTO.getEmail());
        if(test!=null) {
            logger.info("User already exists -->"+userDTO.getEmail());
            throw new EmailAlreadyExistsException("User with this email already exists.....");
        }

        UserMapper mapper = new UserMapper();
        User user=mapper.getUserEntity(userDTO);

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(userDTO.getPassword()));

        if(user.getRole()==null) user.setRole(Role.USER);
        user.setProvider(provider);

        user= this.userRepository.save(user);
        logger.info("User successfully saved....");


        return userDTO;
    }
}
