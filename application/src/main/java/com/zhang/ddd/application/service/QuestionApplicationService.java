package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
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

    @Transactional
    public Question create(String title, String body, String authorId, List<String> tagLabels) {

        Question question = questionDomainService.create(title, body, authorId, tagLabels);
        // TODO: use event maybe
        userDomainService.userCreateQuestion(authorId);

        return question;
    }

    @Transactional
    public Answer createAnswer(String questionId, String body, String authorId) {
        Answer answer = answerDomainService.create(questionId, body, authorId);
        // TODO: use event maybe
        userDomainService.userCreateAnswer(authorId);
        return  answer;
    }
}
