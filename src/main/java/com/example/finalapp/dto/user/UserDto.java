package com.example.finalapp.dto.user;

import lombok.*;

import java.util.Objects;


@Getter @Setter @ToString
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String loginId;
    private String password;
    private String gender;
    private String email;
    private String address;
    private String addressDetail;
    private String zipcode;
}











