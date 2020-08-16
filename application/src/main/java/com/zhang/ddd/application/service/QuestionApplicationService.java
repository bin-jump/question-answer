package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.favor.service.FeedDomainService;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.service.AnswerDomainService;
import com.zhang.ddd.domain.aggregate.post.service.QuestionDomainService;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.aggregate.user.service.UserDomainService;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionApplicationService {

    @Autowired
    QuestionDomainService questionDomainService;

    @Autowired
    UserDomainService userDomainService;

    @Autowired
    AnswerDomainService answerDomainService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FeedDomainService feedDomainService;

    @Transactional
    public Question create(String title, String body, String authorId, List<String> tagLabels) {

        // do not need to check user existence as user will be updated
        Question question = questionDomainService.create(title, body, authorId, tagLabels);
        userDomainService.userCreateQuestion(authorId);

        feedDomainService.questionCreate(authorId, question.getId());

        return question;
    }

    @Transactional
    public Answer createAnswer(String questionId, String body, String authorId) {

        // do not need to check user existence as user will be updated
        Answer answer = answerDomainService.create(questionId, body, authorId);
        userDomainService.userCreateAnswer(authorId);

        feedDomainService.answerCreate(authorId, answer.getId());

        return  answer;
    }

    @Transactional
    public Comment addQuestionComment(String authorId, String questionId, String body) {
        User user = userRepository.findById(authorId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Comment comment = questionDomainService.createQuestionComment(authorId, questionId, body);
        return comment;
    }

    @Transactional
    public Comment addAnswerComment(String authorId, String answerId, String body) {
        User user = userRepository.findById(authorId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Comment comment = answerDomainService.createAnswerComment(authorId, answerId, body);
        return comment;
    }


}
