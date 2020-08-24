package com.zhang.ddd.domain.aggregate.post.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;

public interface AnswerRepository {

    Long nextId();

    void save(Answer answer);

    void update(Answer answer);

    Answer findById(Long id);

    List<Answer> findByIds(List<Long> ids);

    List<Answer> findByQuestionId(Long questionId, PostPaging postPaging);

    List<Answer> findQuestionLatestAnswers(List<Long> questionIds);

    List<Answer> findByUserId(Long authorId, PostPaging postPaging);

}
