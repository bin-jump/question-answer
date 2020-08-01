package com.zhang.ddd.application.service;

import com.zhang.ddd.application.service.UserApplicationService;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.presentation.SmartpostApplication;
import com.zhang.ddd.presentation.facade.UserServiceFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ComponentScan("com.zhang.ddd")
@SpringBootTest(classes = SmartpostApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Slf4j
public class UserServiceTest {

    @Autowired
    UserApplicationService userApplicationService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreate() {
        String name = "user1";
        User user = userRepository.findByName(name);
        if (user == null) {
            user = userApplicationService.create(name, "123456", "test@example.com");
        }

        Assert.assertNotNull(user);
    }
}
