package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.exception.ConcurrentUpdateException;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.domain.shared.SequenceRepository;
import com.zhang.ddd.infrastructure.persistence.assembler.QuestionAssembler;
import com.zhang.ddd.infrastructure.persistence.assembler.TagAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.QuestionMapper;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.QuestionTagMapper;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.TagMapper;
import com.zhang.ddd.infrastructure.persistence.po.QuestionPO;
import com.zhang.ddd.infrastructure.persistence.po.QuestionTag;
import com.zhang.ddd.infrastructure.persistence.po.TagPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionTagMapper questionTagMapper;

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
    public void save(Question question) {
        QuestionPO questionPO = QuestionAssembler.toPO(question);
        questionMapper.insert(questionPO);
        for (TagPO t : questionPO.getTags()) {
            QuestionTag questionTag = new QuestionTag(questionPO.getId(), t.getId());
            questionTagMapper.insert(questionTag);
        }
    }

    @Override
    public void update(Question question) {
        QuestionPO questionPO = QuestionAssembler.toPO(question);
        int num = questionMapper.update(questionPO);
        if (num == 0) {
            throw new ConcurrentUpdateException("Question concurrent update.");
        }
    }

    @Override
    public Question findById(String id) {
        long qid = NumberEncoder.decode(id);
        QuestionPO questionPO = questionMapper.findById(qid);
        tagMapper.findByQuestionIds(Arrays.asList(qid))
                .stream().forEach(e -> questionPO.getTags().add(e));

        return QuestionAssembler.toDO(questionPO);
    }

    @Override
    public List<Question> findByIds(List<String> ids) {
        List<Long> qids = ids.stream()
                .map(NumberEncoder::decode).collect(Collectors.toList());

        List<Question> questionPOs = questionMapper.findByIds(qids)
                .stream().map(QuestionAssembler::toDO)
                .collect(Collectors.toList());

        return questionPOs;
    }

    @Override
    public List<Question> findQuestions(PostPaging postPaging) {

        String sortKey = "id";
        Long cursor = NumberEncoder.decode(postPaging.getCursor());
        List<QuestionPO> questionPOs =
                questionMapper.findQuestions(cursor, postPaging.getSize(), sortKey);
        fillQuestionTags(questionPOs);

        return QuestionAssembler.toDOs(questionPOs);
    }

    @Override
    public List<Question> findByUserId(String authorId, PostPaging postPaging) {
        long aid = NumberEncoder.decode(authorId);
        String sortKey = "id";
        Long cursor = NumberEncoder.decode(postPaging.getCursor());

        List<QuestionPO> questionPOs =
                questionMapper.findByUserId(aid, cursor, postPaging.getSize(), sortKey);
        fillQuestionTags(questionPOs);

        return QuestionAssembler.toDOs(questionPOs);
    }

    private void fillQuestionTags(List<QuestionPO> questionPOs) {
        List<Long> ids = questionPOs.stream()
                .map(QuestionPO::getId).collect(Collectors.toList());
        List<QuestionTag> questionTags = questionTagMapper.findByQuestionIds(ids);

        Map<Long, QuestionPO> qs = questionPOs.stream()
                .collect(Collectors.toMap(QuestionPO::getId, e -> e));


        questionTags.stream().forEach(e -> {
            QuestionPO q = qs.get(e.getQuestionId());
            q.getTags().add(e.getTagPO());
        });

    }

}
