package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.presentation.SmartpostApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@ComponentScan("com.zhang.ddd")
@SpringBootTest(classes = SmartpostApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Slf4j
public class QuestionServiceTest {

    @Autowired
    QuestionApplicationService questionApplicationService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreate() {

        User user = userRepository.findByName("zhang");

        Question question = questionApplicationService.create("this is a question.",
                "body", user.getId(), Arrays.asList("tag a", "tag c"));

        Assert.assertNotNull(question);
    }
}
