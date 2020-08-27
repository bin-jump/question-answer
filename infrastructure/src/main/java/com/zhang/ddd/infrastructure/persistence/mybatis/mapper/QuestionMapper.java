package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import java.util.List;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.infrastructure.persistence.po.QuestionPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    int insert(QuestionPO questionPO);

    int update(QuestionPO questionPO);

    QuestionPO findById(long questionId);

    List<QuestionPO> findByIds(List<Long> questionIds);

    List<QuestionPO> findQuestions(Long cursor, int size, String sortKey);

    List<QuestionPO> findByUserId(long authorId, Long cursor, int size, String sortKey);

    List<QuestionPO> findHotQuestions(int topNum, int size);

    List<QuestionPO> randomLowAnswerQuestion(long seed, int topNum, int offset, int size);

}
