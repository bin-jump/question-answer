package com.zhang.ddd.infrastructure.persistence.mybatis;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.domain.aggregate.post.repository.TagRepository;
import com.zhang.ddd.domain.shared.SequenceRepository;
import com.zhang.ddd.infrastructure.persistence.assembler.TagAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.TagMapper;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public Long nextId() {
        long id = sequenceRepository.nextId();
        return id;
    }


    @Override
    public void save(Tag tag) {
        tagMapper.insert(TagAssembler.toPO(tag));
    }

    public List<Tag> findByLabels(List<String> labels) {
        return TagAssembler.toDOs(tagMapper.findByLabels(labels));
    }

    public List<Tag> findByIds(List<Long> ids) {

        return TagAssembler.toDOs(tagMapper.findByIds(ids));
    }
}
