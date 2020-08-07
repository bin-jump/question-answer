package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.SequenceMapper;
import com.zhang.ddd.domain.shared.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SequenceRepositoryImpl implements SequenceRepository {

    @Autowired
    SequenceMapper sequenceMapper;

    @Override
    public long nextId() {
        Map<String, Object> res = new HashMap<>();
        sequenceMapper.insert(res);

        return (Long)res.get("id");
    }
}
