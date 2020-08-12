package com.zhang.ddd.domain.aggregate.user.entity;

import com.zhang.ddd.domain.aggregate.user.entity.valueobject.UserGender;
import com.zhang.ddd.domain.exception.InvalidValueException;
import com.zhang.ddd.domain.shared.Entity;
import com.zhang.ddd.domain.util.UserPasswordEncoder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;

// TODO: write correct setters
@Getter
@Setter
@NoArgsConstructor
public class User extends Entity<User> {


    public User(String id, String name, String password) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidValueException("User name can not be empty.");
        }

        this.name = name;
        this.id = id;
        updatePassword(password);
        this.created = new Date();
    }

    private String name;

    private String password;

    private String avatarUrl;

    private String email;

    private int age;

    private UserGender gender = UserGender.UNSET;

    private String description;

    private Date created;

    // counts of other aggregates
    private int questionCount;

    private int answerCount;

    private int followCount;

    private int followerCount;

    private int followeeCount;

    public void updatePassword(String password) {
        if (!StringUtils.hasText(password) || password.length() < 6) {
            throw new InvalidValueException("Password must have length longer than 6.");
        }
        this.password = UserPasswordEncoder.encode(password);
    }

    public void setEmail(String email) {
        if (email == null || !email
                .matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")) {
            throw new InvalidValueException("Not a valid email.");
        }
        this.email = email;
    }

    public void setAge(int age) {
        if ( 150 < age) {
            throw new InvalidValueException("Age is not valid.");
        }
        this.age = age;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
