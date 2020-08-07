package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface SequenceMapper {

    long insert(Map<String, Object> map);
}
