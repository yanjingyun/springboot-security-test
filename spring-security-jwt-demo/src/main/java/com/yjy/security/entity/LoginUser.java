package com.yjy.security.entity;

import lombok.Data;

@Data
public class LoginUser {

    private String username;
    private String password;
    private Boolean rememberMe;

}
