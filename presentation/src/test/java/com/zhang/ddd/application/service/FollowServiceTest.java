package com.zhang.ddd.application.service;


import com.zhang.ddd.infrastructure.util.SequenceRepository;
import com.zhang.ddd.presentation.SmartpostApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ComponentScan("com.zhang.ddd")
@SpringBootTest(classes = SmartpostApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Slf4j
public class FollowServiceTest {

    @Autowired
    SequenceRepository sequenceRepository;

    @Test
    public void sequenceTest() {
        long id = sequenceRepository.nextId();

        Assert.assertTrue(id > 0);
    }
}
