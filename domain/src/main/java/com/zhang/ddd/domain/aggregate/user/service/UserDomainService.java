package com.zhang.ddd.domain.aggregate.user.service;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.AvatarImageRepository;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.aggregate.user.service.command.ChangeAvatarCommand;
import com.zhang.ddd.domain.aggregate.user.service.command.EditUserCommand;
import com.zhang.ddd.domain.exception.InvalidOperationException;
import com.zhang.ddd.domain.exception.InvalidValueException;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.domain.util.UserPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

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

    public User editUser(EditUserCommand editUserCommand) {
        User user = userRepository.findById(editUserCommand.getId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        user.setEmail(editUserCommand.getEmail());
        user.setAge(editUserCommand.getAge());
        user.setDescription(editUserCommand.getDescription());
        user.setGender(editUserCommand.getGender());

        userRepository.update(user);
        return user;
    }

    public User changeAvatarImage(ChangeAvatarCommand command) {
        User user = userRepository.findById(command.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        String currentImage = user.getAvatarUrl();
        String newImage = avatarImageRepository
                .saveImage(user.getName(), command.getFileName(), command.getFileSize(), command.getStream());
        user.setAvatarUrl(newImage);

        userRepository.update(user);
        avatarImageRepository.removeImage(currentImage);
        return user;
    }

    public void changePassword(String id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!UserPasswordEncoder
                .check(user.getPassword(), oldPassword))
        {
            throw new InvalidOperationException("Wrong password.");
        }

        user.updatePassword(newPassword);
        userRepository.update(user);
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

    public void questionFollowed(String followerId, boolean follow) {
        User follower = userRepository.findById(followerId);
        if (follower == null) {
            throw new ResourceNotFoundException("User not found");
        }
        int diff = follow ? 1 : -1;
        follower.setFollowCount(follower.getFollowCount() + diff);
        userRepository.update(follower);
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
