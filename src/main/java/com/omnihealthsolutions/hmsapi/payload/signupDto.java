package com.omnihealthsolutions.hmsapi.payload;

import lombok.Data;

@Data
public class signupDto {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String username;
}
