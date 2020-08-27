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
    public Long nextId() {
        long id = sequenceRepository.nextId();
        return id;
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
    public Question findById(Long id) {

        QuestionPO questionPO = questionMapper.findById(id);
        //tagMapper.findByQuestionIds(Arrays.asList(id))
        //        .stream().forEach(e -> questionPO.getTags().add(e));
        fillQuestionTags(Arrays.asList(questionPO));

        return QuestionAssembler.toDO(questionPO);
    }

    @Override
    public List<Question> findByIds(List<Long> ids) {

        List<Question> questionPOs = questionMapper.findByIds(ids)
                .stream().map(QuestionAssembler::toDO)
                .collect(Collectors.toList());

        return questionPOs;
    }

    @Override
    public List<Question> findQuestions(PostPaging postPaging) {

        String sortKey = "id";
        Long cursor = postPaging.getCursor();
        List<QuestionPO> questionPOs =
                questionMapper.findQuestions(cursor, postPaging.getSize(), sortKey);
        fillQuestionTags(questionPOs);

        return QuestionAssembler.toDOs(questionPOs);
    }

    @Override
    public List<Question> findByUserId(Long authorId, PostPaging postPaging) {

        String sortKey = "id";
        Long cursor = postPaging.getCursor();
        List<QuestionPO> questionPOs =
                questionMapper.findByUserId(authorId, cursor, postPaging.getSize(), sortKey);
        fillQuestionTags(questionPOs);

        return QuestionAssembler.toDOs(questionPOs);
    }

    @Override
    public List<Question> findHotQuestions(int limit) {
        // TODO: use both percentage with constant
        int topNum = 1000;
        List<QuestionPO> questionPOs = questionMapper.findHotQuestions(topNum, limit);
        return questionPOs.stream().map(QuestionAssembler::toDO).collect(Collectors.toList());
    }

    @Override
    public List<Question> randomLowAnswerQuestion(int seed, PostPaging postPaging) {
        // could get duplicate question when new question added
        // TODO: use both percentage with constant
        int topNum = 1000;
        if (postPaging.getOffset() > topNum) {
            return new ArrayList<>();
        }
        List<QuestionPO> questionPOs = questionMapper.randomLowAnswerQuestion(seed, topNum,
                postPaging.getOffset(), postPaging.getSize());

        return questionPOs.stream().map(QuestionAssembler::toDO).collect(Collectors.toList());
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
