package com.zhang.ddd.domain.aggregate.post.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;

public interface AnswerRepository {

    String nextId();

    void save(Answer answer);

    void update(Answer answer);

    Answer findById(String id);

    List<Answer> findByIds(List<String> ids);

    List<Answer> findByQuestionId(String questionId, PostPaging postPaging);

    List<Answer> findQuestionLatestAnswers(List<String> questionIds);

    List<Answer> findByUserId(String authorId, PostPaging postPaging);

}
