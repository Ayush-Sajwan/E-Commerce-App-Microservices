package com.user_auth.mapper;

import com.user_auth.dto.UserDTO;
import com.user_auth.entity.User;

public class UserMapper {

    public UserDTO getUserDto(User user){

        return new UserDTO(user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getProvider(),
                user.getPassword());
    }

    public User getUserEntity(UserDTO user){

        User user1=new User();
        if(user.getId()!=null) user1.setId(user.getId());
        user1.setEmail(user.getEmail());
        user1.setFullName(user.getFullName());
        user1.setRole(user.getRole());
        user1.setProvider(user.getProvider());
        user1.setPassword(user.getPassword());

        return user1;

    }
}
