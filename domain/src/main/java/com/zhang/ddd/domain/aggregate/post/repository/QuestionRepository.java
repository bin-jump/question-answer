package com.zhang.ddd.domain.aggregate.post.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;

public interface QuestionRepository {

    String nextId();

    void save(Question question);


    Question findById(String id);

}
