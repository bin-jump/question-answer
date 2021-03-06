package com.zhang.ddd.domain.aggregate.post.service;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.CommentRepository;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.post.repository.SearchPostRepository;
import com.zhang.ddd.domain.exception.InvalidOperationException;
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

    @Autowired
    SearchPostRepository searchPostRepository;

    public Answer create(Long questionId, String body, Long authorId) {
        Question question = questionRepository.findById(questionId);
        if (question == null) {
            throw new ResourceNotFoundException("Question not found");
        }

        Long id = answerRepository.nextId();
        Answer answer = new Answer(id, questionId, body, authorId);
        answerRepository.save(answer);

        question.setAnswerCount(question.getAnswerCount() + 1);
        questionRepository.update(question);

        SearchItem searchItem = SearchItem.fromAnswer(answer, question.getTitle());
        searchPostRepository.save(searchItem);

        return answer;
    }

    public Comment createAnswerComment(Long authorId, Long answerId, String body) {

        Answer answer = answerRepository.findById(answerId);
        if (answer == null) {
            throw new ResourceNotFoundException("Answer not found");
        }

        Long id = commentRepository.nextId();
        Comment comment = new Comment(id, authorId, answerId, body, CommentResourceType.ANSWER);
        commentRepository.save(comment);

        answer.setCommentCount(answer.getCommentCount() + 1);
        answerRepository.update(answer);

        return comment;
    }

    public void answerVoted(Long answerId, int upvoteDiff, int downvoteDiff){


        Answer answer = answerRepository.findById(answerId);
        if (answer == null) {
            throw new ResourceNotFoundException("Answer not found");
        }

        answer.setUpvoteCount(answer.getUpvoteCount() + upvoteDiff);
        answer.setDownvoteCount(answer.getDownvoteCount() + downvoteDiff);

        answerRepository.update(answer);
    }
}
