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
    public String nextId() {
        long id = sequenceRepository.nextId();
        return NumberEncoder.encode(id);
    }


    @Override
    public void save(Tag tag) {
        tagMapper.insert(TagAssembler.toPO(tag));
    }

    public List<Tag> findByLabels(List<String> labels) {
        return TagAssembler.toDOs(tagMapper.findByLabels(labels));
    }

    public List<Tag> findByIds(List<String> ids) {
        List<Long> tids = ids.stream().map(NumberEncoder::decode).collect(Collectors.toList());
        return TagAssembler.toDOs(tagMapper.findByIds(tids));
    }
}
