package com.zhang.ddd.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String id;

    private String name;

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
