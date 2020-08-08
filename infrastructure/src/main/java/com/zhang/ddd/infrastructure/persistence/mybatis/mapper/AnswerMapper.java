package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import java.util.List;
import com.zhang.ddd.infrastructure.persistence.po.AnswerPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnswerMapper {

    int insert(AnswerPO answerPO);

    int update(AnswerPO answerPO);

    AnswerPO findById(long id);

    List<AnswerPO> findByQuestionId(long questionId, Long cursor, int size, String sortKey);

    List<AnswerPO> findByIds(List<Long> answerIds);

    List<AnswerPO> findQuestionLatestAnswers(List<Long> questionIds);

    List<AnswerPO> findByUserId(long authorId, Long cursor, int size, String sortKey);


}
