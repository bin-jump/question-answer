package com.zhang.ddd.domain.aggregate.post.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;

public interface QuestionRepository {

    Long nextId();

    void save(Question question);

    void update(Question question);

    Question findById(Long id);

    List<Question> findByIds(List<Long> ids);

    List<Question> findQuestions(PostPaging postPaging);

    List<Question> findByUserId(Long authorId, PostPaging postPaging);
}
