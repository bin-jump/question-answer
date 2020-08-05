package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import java.util.List;
import com.zhang.ddd.infrastructure.persistence.po.QuestionTag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionTagMapper {

    void insert(QuestionTag questionTag);

    List<QuestionTag> findByQuestionIds(List<String> questionIds);
}
