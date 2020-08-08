package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.exception.ConcurrentUpdateException;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.domain.shared.SequenceRepository;
import com.zhang.ddd.infrastructure.persistence.assembler.AnswerAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.AnswerMapper;
import com.zhang.ddd.infrastructure.persistence.po.AnswerPO;
import com.zhang.ddd.infrastructure.persistence.po.QuestionPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository {

    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public String nextId() {
        long id = sequenceRepository.nextId();
        return NumberEncoder.encode(id);
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
        long aid = NumberEncoder.decode(id);
        AnswerPO answerPO = answerMapper.findById(aid);
        return AnswerAssembler.toDO(answerPO);

    }

    @Override
    public List<Answer> findByQuestionId(String questionId, PostPaging postPaging) {
        long qid = NumberEncoder.decode(questionId);
        String sortKey = "id";
        Long cursor = NumberEncoder.decode(postPaging.getCursor());

        List<AnswerPO> answerPOs =
                answerMapper.findByQuestionId(qid, cursor, postPaging.getSize(), sortKey);

        return AnswerAssembler.toDOs(answerPOs);

    }

    @Override
    public List<Answer> findQuestionLatestAnswers(List<String> questionIds) {
        List<Long> qids = questionIds.stream().map(NumberEncoder::decode).collect(Collectors.toList());
        List<AnswerPO> answerPOs = answerMapper.findQuestionLatestAnswers(qids);

        return AnswerAssembler.toDOs(answerPOs);
    }

    @Override
    public List<Answer> findByUserId(String authorId, PostPaging postPaging) {
        String sortKey = "id";
        long aid = NumberEncoder.decode(authorId);
        Long cursor = NumberEncoder.decode(postPaging.getCursor());
        List<AnswerPO> answerPOs =
                answerMapper.findByUserId(aid, cursor, postPaging.getSize(), sortKey);

        return AnswerAssembler.toDOs(answerPOs);
    }
}
