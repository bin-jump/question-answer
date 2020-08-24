package com.zhang.ddd.domain.aggregate.post.repository;

import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import java.util.List;

public interface TagRepository {

    Long nextId();

    void save(Tag tag);

    List<Tag> findByLabels(List<String> labels);

    List<Tag> findByIds(List<Long> ids);
}
