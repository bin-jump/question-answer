package com.zhang.ddd.application.service;

import com.zhang.ddd.application.service.UserApplicationService;
import com.zhang.ddd.presentation.SmartpostApplication;
import com.zhang.ddd.presentation.facade.UserServiceFacade;
import lombok.extern.slf4j.Slf4j;
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
public class UserDomainServiceTest {

    @Autowired
    UserApplicationService userApplicationService;

    @Test
    public void test() {
        userApplicationService.create("user1", "123456");
    }
}
