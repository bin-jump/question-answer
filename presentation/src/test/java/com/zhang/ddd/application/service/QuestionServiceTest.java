package com.zhang.ddd.application.service;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
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
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@ComponentScan("com.zhang.ddd")
@SpringBootTest(classes = SmartpostApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Slf4j
public class QuestionServiceTest {

    @Autowired
    QuestionApplicationService questionApplicationService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Test
    public void testCreate() {

        User user = userRepository.findByName("zhang");

        Question question = questionApplicationService.create("this is a question.",
                "body", user.getId(), Arrays.asList("tag a", "tag c"));

        Assert.assertNotNull(question);
    }

    @Test
    public void testCreateAnswer() {

        User user = userRepository.findByName("zhang");
        Answer answer = questionApplicationService.createAnswer("8f7e6ae5a6614af095763d7f17c12715"
        ,"answer body", user.getId());

        Assert.assertNotNull(answer);
    }

    @Test
    public void testCreateQuestionComment() {

        User user = userRepository.findByName("zhang");
        List<Question> questions = questionRepository.findQuestions(new PostPaging(null, 10));

        Comment comment = questionApplicationService
                .addQuestionComment(user.getId(), questions.get(0).getId(), "This is a comment.");

        Assert.assertNotNull(comment);
    }

    @Test
    public void testCreateAnswerComment() {

        User user = userRepository.findByName("zhang");
        List<Question> questions = questionRepository.findQuestions(new PostPaging(null, 10));
        List<Answer> answers = answerRepository.findQuestionLatestAnswers(questions.stream()
                .map(Question::getId).collect(Collectors.toList()));

        Comment comment = questionApplicationService
                .addAnswerComment(user.getId(), answers.get(0).getId(), "This is a answer comment.");

        Assert.assertNotNull(comment);
    }

    @Test
    public void testGetQuestion() {
        List<Question> questions = questionRepository.findQuestions(new PostPaging(null, 10));
        List<Answer> answers = answerRepository.findQuestionLatestAnswers(questions.stream()
                .map(Question::getId).collect(Collectors.toList()));

        Assert.assertTrue(questions.size() > 0);
        Assert.assertTrue(answers.size() > 0);
    }

    @Test
    public void testGetAnswer() {
        Question question = questionRepository.findById("8f7e6ae5a6614af095763d7f17c12715");

        List<Answer> answers = answerRepository.findByQuestionId("8f7e6ae5a6614af095763d7f17c12715",
                new PostPaging(null, 10));

        Assert.assertTrue(answers.size() > 0);
    }
}
