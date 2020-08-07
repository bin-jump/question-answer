package com.zhang.ddd.application.service;


import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.repository.FollowRepository;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.shared.SequenceRepository;
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

    static final String testUserName = "zhang";

    @Autowired
    SequenceRepository sequenceRepository;

    @Autowired
    FavorApplicationService favorApplicationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    FollowRepository followRepository;

    @Test
    public void sequenceTest() {
        long id = sequenceRepository.nextId();
        Assert.assertTrue(id > 0);
    }

    @Test
    public void followQuestionTest() {
        User user = userRepository.findByName(testUserName);
        Question question = questionRepository.findQuestions(new PostPaging(null, 10))
                .get(0);

        favorApplicationService.unfollowQuestion(user.getId(), question.getId());

        favorApplicationService.followQuestion(user.getId(), question.getId());
        Follow follow = followRepository.find(user.getId(), question.getId(), FollowResourceType.QUESTION);

        Assert.assertNotNull(follow);
    }

    @Test
    public void followUserTest() {
        User user = userRepository.findByName(testUserName);
        User followee = userRepository.findByName("user1");

        favorApplicationService.unfollowUser(user.getId(), followee.getId());

        favorApplicationService.followUser(user.getId(), followee.getId());
        Follow follow = followRepository.find(user.getId(), followee.getId(), FollowResourceType.USER);

        Assert.assertNotNull(follow);
    }
}
