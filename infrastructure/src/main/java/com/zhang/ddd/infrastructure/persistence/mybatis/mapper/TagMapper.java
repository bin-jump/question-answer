package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import java.util.List;
import com.zhang.ddd.infrastructure.persistence.po.TagPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {

    int insert(TagPO tagPO);

    List<TagPO> findByIds(List<String> tagIds);

    List<TagPO> findByLabels(List<String> labels);

    List<TagPO> findByQuestionIds(List<String> questionIds);

}
