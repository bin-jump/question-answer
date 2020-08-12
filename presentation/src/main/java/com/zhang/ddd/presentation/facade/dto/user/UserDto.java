package com.zhang.ddd.presentation.facade.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String id;

    private String cursor;

    @NotBlank
    private String name;

    private String password;

    private int age;

    private String gender;

    private String description;

    @Email(message = "Invalid email")
    private String email;

    private String avatarUrl;

    private long created;

    private boolean following;

    private int answerCount;

    private int questionCount;

    private int followCount;

    private int followerCount;

    private int followeeCount;

}
