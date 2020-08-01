package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.service.UserDomainService;
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

}
