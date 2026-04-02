package com.user_auth.controller;

import com.user_auth.dto.UserDTO;
import com.user_auth.dto.groups.UserDtoGroups;
import com.user_auth.entity.Role;
import com.user_auth.entity.User;
import com.user_auth.response.ApiResponse;
import com.user_auth.services.JwtService;
import com.user_auth.services.MyUserDetails;
import com.user_auth.services.UserManagingService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    private UserManagingService userManagingService;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    public UserController(UserManagingService userManagingService,AuthenticationManager authenticationManager,JwtService jwtService){
        this.userManagingService=userManagingService;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> userLogin(@Validated(UserDtoGroups.loginUserDtoGroup.class)
                                                             @RequestBody UserDTO userDTO){
        System.out.println("User obj-->"+userDTO);

        try {

            Authentication authentication=
                    this.authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(userDTO.getEmail(),userDTO.getPassword())
                    );

            if (authentication.isAuthenticated()) {

                //getting the user details object
                MyUserDetails myUserDetails=(MyUserDetails)(authentication.getPrincipal());

                String jwt = this.jwtService.generateJwtToken(userDTO.getEmail(), List.of("USER"),myUserDetails.getUserId());
                return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(
                        new ApiResponse<>("Logged In Successfully...",jwt)
                );

            }

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(
                    new ApiResponse<>("Invalid Credentials..",null)
            );

        }catch (Exception e){
            logger.info("Exception occurred.."+e.getMessage());
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(
                    new ApiResponse<>("Error occurred while logging in..",null)
            );
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDTO>> userSignUp(@Validated(UserDtoGroups.createUserDtoGroup.class)
                                                               @RequestBody UserDTO userDTO){

        System.out.println("User obj-->"+userDTO);

        userDTO=this.userManagingService.saveUser(userDTO,"App");
        userDTO.setPassword("##########");

        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                    .body(new ApiResponse<>("User successfully saved..",userDTO));

    }


    @GetMapping("/testing/{user}")
    public ResponseEntity<String> testingUser(@PathVariable("user") String user){

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Successfully able to access.."+user);
    }
}
