package com.zhang.ddd.domain.aggregate.post.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;

public interface QuestionRepository {

    String nextId();

    void save(Question question);

    void update(Question question);

    Question findById(String id);

    List<Question> findByIds(List<String> ids);

    List<Question> findQuestions(PostPaging postPaging);

    List<Question> findByUserId(String authorId, PostPaging postPaging);
}
