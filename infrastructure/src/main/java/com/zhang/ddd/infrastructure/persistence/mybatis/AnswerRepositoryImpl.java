package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.exception.ConcurrentUpdateException;
import com.zhang.ddd.infrastructure.persistence.assembler.AnswerAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.AnswerMapper;
import com.zhang.ddd.infrastructure.persistence.po.AnswerPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository {

    @Autowired
    AnswerMapper answerMapper;

    @Override
    public String nextId() {
        final String random = UUID.randomUUID().toString()
                .toLowerCase().replace("-", "");
        return random;
    }

    @Override
    public void save(Answer answer) {
        answerMapper.insert(AnswerAssembler.toPO(answer));
    }

    @Override
    public void update(Answer answer) {
        int num = answerMapper.update(AnswerAssembler.toPO(answer));
        if (num == 0) {
            throw new ConcurrentUpdateException("Answer concurrent update.");
        }
    }

    @Override
    public Answer findById(String id) {
        AnswerPO answerPO = answerMapper.findById(id);
        return AnswerAssembler.toDO(answerPO);

    }

    @Override
    public List<Answer> findByQuestionId(String questionId) {
        return null;
    }
}
