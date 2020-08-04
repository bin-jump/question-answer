package com.zhang.ddd.domain.aggregate.post.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;

public interface AnswerRepository {

    String nextId();

    void save(Answer answer);

    void update(Answer answer);

    Answer findById(String id);

    List<Answer> findByQuestionId(String questionId);

}
