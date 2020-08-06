package com.zhang.ddd.application.service;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.Vote;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResourceType;
import com.zhang.ddd.domain.aggregate.vote.repository.VoteRepository;
import com.zhang.ddd.presentation.SmartpostApplication;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
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
public class VoteServiceTest {

    static final String testUserName = "zhang";

    @Autowired
    VoteApplicationService voteApplicationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    VoteRepository voteRepository;

    @Test
    public void voteQuestionTest() {
        User user = userRepository.findByName(testUserName);
        Question question = questionRepository.findQuestions(new PostPaging(null, 10))
                .get(0);

        voteApplicationService.unvoteQuestion(user.getId(), question.getId());
        int currentUpvoteCount = question.getUpvoteCount();
        voteApplicationService.voteQuestion(user.getId(), question.getId(), true);
        Vote vote = voteRepository.find(user.getId(), question.getId(), VoteResourceType.QUESTION);
        int newUpvoteCount = questionRepository.findById(question.getId()).getUpvoteCount();
        Assert.assertNotNull(vote);
        Assert.assertEquals(currentUpvoteCount + 1, newUpvoteCount);
    }


    @Test
    public void voteAnswerTest() {
        User user = userRepository.findByName(testUserName);
        List<String> questionIds =
                questionRepository.findQuestions(new PostPaging(null, 10))
                .stream().map(Question::getId).collect(Collectors.toList());
        Answer answer = answerRepository.findQuestionLatestAnswers(questionIds).get(0);

        voteApplicationService.unvoteAnswer(user.getId(), answer.getId());
        int currentUpvoteCount = answer.getUpvoteCount();
        voteApplicationService.voteAnswer(user.getId(), answer.getId(), true);
        Vote vote = voteRepository.find(user.getId(), answer.getId(), VoteResourceType.ANSWER);
        int newUpvoteCount = answerRepository.findById(answer.getId()).getUpvoteCount();
        Assert.assertNotNull(vote);
        Assert.assertEquals(currentUpvoteCount + 1, newUpvoteCount);
    }

}
