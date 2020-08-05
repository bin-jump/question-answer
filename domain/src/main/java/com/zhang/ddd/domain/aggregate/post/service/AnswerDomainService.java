package com.zhang.ddd.domain.aggregate.post.service;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.CommentRepository;
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

    @Autowired
    CommentRepository commentRepository;

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

    public Comment createAnswerComment(String authorId, String answerId, String body) {

        Answer answer = answerRepository.findById(answerId);
        if (answer == null) {
            throw new ResourceNotFoundException("Answer not found");
        }

        String id = commentRepository.nextId();
        Comment comment = new Comment(id, authorId, answerId, body, CommentResourceType.ANSWER);
        commentRepository.save(comment);

        answer.setCommentCount(answer.getCommentCount() + 1);
        answerRepository.update(answer);

        return comment;
    }
}
