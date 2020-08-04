package com.zhang.ddd.domain.aggregate.post.service;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerDomainService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    QuestionRepository questionRepository;

    public Answer create(String questionId, String body, String authorId) {
        Question question = questionRepository.findById(questionId);
        if (question == null) {
            throw new ResourceNotFoundException("Question not found");
        }

        String id = answerRepository.nextId();
        Answer answer = new Answer(id, questionId, body, authorId);
        answerRepository.save(answer);

        question.setAnswerCount(question.getAnswerCount() + 1);
        questionRepository.update(question);

        return answer;
    }
}
