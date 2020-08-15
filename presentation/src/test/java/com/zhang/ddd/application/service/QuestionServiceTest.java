package com.zhang.ddd.application.service;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.post.repository.SearchPostRepository;
import com.zhang.ddd.domain.aggregate.post.service.PostSearchDomainService;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.infrastructure.search.post.SearchPostRepositoryImpl;
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

    static String testUserName = "zhang";

    @Autowired
    QuestionApplicationService questionApplicationService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    PostSearchDomainService searchDomainService;

    @Autowired
    SearchPostRepository searchPostRepository;

    @Test
    public void searchTest() {
        List<SearchItem> items = searchDomainService.search("question", null, 10);

        //Assert.assertTrue(items.size() > 0);
        //((SearchPostRepositoryImpl)searchPostRepository).createIndex();

    }

    @Test
    public void createQuestionTest() {

        User user = userRepository.findByName(testUserName);
        Question question = questionApplicationService.create("This is a new question.",
                "body", user.getId(), Arrays.asList("tag a", "tag c"));

        Assert.assertNotNull(question);
    }

    @Test
    public void testCreateAnswer() {

        User user = userRepository.findByName(testUserName);
        List<Question> questions = questionRepository.findQuestions(new PostPaging(null, 10));
        Answer answer = questionApplicationService.createAnswer(questions.get(0).getId(),
                "answer body", user.getId());

        Assert.assertNotNull(answer);
    }

    @Test
    public void testCreateQuestionComment() {

        User user = userRepository.findByName(testUserName);
        List<Question> questions = questionRepository.findQuestions(new PostPaging(null, 10));

        Comment comment = questionApplicationService
                .addQuestionComment(user.getId(), questions.get(0).getId(), "This is a comment.");

        Assert.assertNotNull(comment);
    }

    @Test
    public void testCreateAnswerComment() {

        User user = userRepository.findByName(testUserName);
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
        Question question = questionRepository.findQuestions(new PostPaging(null, 10)).get(0);

        List<Answer> answers = answerRepository.findByQuestionId(question.getId(),
                new PostPaging(null, 10));

        Assert.assertTrue(answers.size() > 0);
    }
}
