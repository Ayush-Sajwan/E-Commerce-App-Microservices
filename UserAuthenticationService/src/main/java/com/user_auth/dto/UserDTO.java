package com.user_auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.user_auth.dto.groups.UserDtoGroups;
import com.user_auth.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserDTO {
    @JsonProperty("fullName")
    @NotEmpty(groups = {UserDtoGroups.createUserDtoGroup.class},
            message = "Username cannot be empty")
    private String fullName;

    @JsonProperty("email")
    @NotEmpty(groups ={UserDtoGroups.createUserDtoGroup.class,UserDtoGroups.loginUserDtoGroup.class} ,
            message = "Email cannot be empty")
    @Email
    private String email;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("password")
    @NotEmpty(groups = {UserDtoGroups.createUserDtoGroup.class,UserDtoGroups.loginUserDtoGroup.class},
            message = "Password cannot be empty")
    private String password;

    @JsonProperty("id")
    private Integer id;

    // Constructors
    public UserDTO() {
        System.out.println("UserDTO object was created....");
    }

    public UserDTO(Integer id,String fullName, String email, Role role, String provider,String password) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.password=password;
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", provider='" + provider + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}