package com.zhang.ddd.domain.aggregate.user.service;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.AvatarImageRepository;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.exception.InvalidValueException;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
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

    public void userCreateQuestion(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        user.setQuestionCount(user.getQuestionCount() + 1);
        userRepository.update(user);
    }

    public void userCreateAnswer(String id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        user.setAnswerCount(user.getAnswerCount() + 1);
        userRepository.update(user);
    }

    public void userFollowed(String followerId, String followeeId, boolean follow) {
        User follower = userRepository.findById(followerId);
        User followee = userRepository.findById(followeeId);

        if (follower == null || followee == null) {
            throw new ResourceNotFoundException("User not found");
        }

        int diff = follow ? 1 : -1;
        follower.setFolloweeCount(follower.getFolloweeCount() + diff);
        followee.setFollowerCount(followee.getFollowerCount() + diff);
        userRepository.update(followee);
        userRepository.update(follower);
    }

}
