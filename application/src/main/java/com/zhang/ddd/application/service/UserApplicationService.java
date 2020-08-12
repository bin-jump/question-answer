package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.service.UserDomainService;
import com.zhang.ddd.domain.aggregate.user.service.command.EditUserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserApplicationService {

    @Autowired
    UserDomainService userDomainService;

    @Transactional
    public User create(String name, String password, String email) {
        return userDomainService.create(name, password, email);
    }

    @Transactional
    public User edit(EditUserCommand editUserCommand) {
        return userDomainService.editUser(editUserCommand);
    }

    @Transactional
    public void changePassword(String id, String oldPassword, String newPassword) {
        userDomainService.changePassword(id, oldPassword, newPassword);
    }

}
