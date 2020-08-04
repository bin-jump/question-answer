package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.exception.ConcurrentUpdateException;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.infrastructure.persistence.assembler.QuestionAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.QuestionMapper;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.QuestionTagMapper;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.TagMapper;
import com.zhang.ddd.infrastructure.persistence.po.QuestionPO;
import com.zhang.ddd.infrastructure.persistence.po.QuestionTag;
import com.zhang.ddd.infrastructure.persistence.po.TagPO;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionTagMapper questionTagMapper;

    @Autowired
    TagMapper tagMapper;

    @Override
    public String nextId() {
        final String random = UUID.randomUUID().toString()
                .toLowerCase().replace("-", "");
        return random;
    }

    @Override
    public void save(Question question) {
        QuestionPO questionPO = QuestionAssembler.toPO(question);
        questionMapper.insert(questionPO);
        for (TagPO t : questionPO.getTags()) {
            QuestionTag questionTag = new QuestionTag(questionPO.getQuestionId(), t.getTagId());
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
        QuestionPO questionPO = questionMapper.findById(id);
        tagMapper.findByQuestionIds(Arrays.asList(id))
                .stream().forEach(e -> questionPO.getTags().add(e));

        return QuestionAssembler.toDO(questionPO);
    }

    @Override
    public List<Question> findQuestions(PostPaging postPaging) {

        String sortKey = "id";
        Long cursor = null;
        if (postPaging.getCursor() != null) {
            QuestionPO cursorQuestion = questionMapper.findById(postPaging.getCursor());
            if (cursorQuestion == null) {
                throw new ResourceNotFoundException("Question not found");
            }
            cursor = cursorQuestion.getId();
        }

        List<QuestionPO> questionPOs =
                questionMapper.findQuestions(cursor, postPaging.getSize(), sortKey);

        return QuestionAssembler.toDOs(questionPOs);
    }

}
