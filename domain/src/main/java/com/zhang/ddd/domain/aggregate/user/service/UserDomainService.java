package com.zhang.ddd.domain.aggregate.user.service;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.AvatarImageRepository;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.exception.InvalidValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDomainService {

    @Autowired
    AvatarImageRepository avatarImageRepository;

    @Autowired
    UserRepository userRepository;

    public User create(String name, String password, String email) {

        User sameNameUser = userRepository.findByName(name);
        if (sameNameUser != null) {
            throw new InvalidValueException("user name already exist.");
        }

        String id = userRepository.nextId();
        User user = new User(id, name, password);
        user.setAvatarUrl(avatarImageRepository.nameHashImage(name));
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

}
