package com.zhang.ddd.infrastructure.persistence.mybatis;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.domain.aggregate.post.repository.TagRepository;
import com.zhang.ddd.infrastructure.persistence.assembler.TagAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @Autowired
    TagMapper tagMapper;

    @Override
    public String nextId() {
        final String random = UUID.randomUUID().toString()
                .toLowerCase().replace("-", "");
        return random;
    }

    @Override
    public void save(Tag tag) {
        tagMapper.insert(TagAssembler.toPO(tag));
    }

    public List<Tag> findByLabels(List<String> labels) {
        return TagAssembler.toDOs(tagMapper.findByLabels(labels));
    }

    public List<Tag> findByIds(List<String> ids) {
        return TagAssembler.toDOs(tagMapper.findByIds(ids));
    }
}
