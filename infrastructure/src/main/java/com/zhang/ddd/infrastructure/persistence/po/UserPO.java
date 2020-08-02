package com.zhang.ddd.infrastructure.persistence.po;

import com.zhang.ddd.domain.aggregate.user.entity.valueobject.UserGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPO {

    private Long id;

    private String userId;

    private long version;

    private String name;

    private String password;

    private String avatarUrl;

    private String email;

    private int age;

    private UserGender gender;

    private String description;

    private Date created;

    private int questionCount;

    private int answerCount;

    private int followerCount;

    private int followeeCount;
}
