package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import java.util.List;
import com.zhang.ddd.infrastructure.persistence.po.AnswerPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnswerMapper {

    int insert(AnswerPO answerPO);

    int update(AnswerPO answerPO);

    AnswerPO findById(String id);

    List<AnswerPO> findByQuestionId(String questionId, Long cursor, int size, String sortKey);

    List<AnswerPO> findByIds(List<String> answerIds);

    List<AnswerPO> findQuestionLatestAnswers(List<String> questionIds);

}
