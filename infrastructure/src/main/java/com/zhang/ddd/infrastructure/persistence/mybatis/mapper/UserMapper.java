package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import com.zhang.ddd.infrastructure.persistence.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insert(UserPO userPO);

    int update(UserPO userPO);

    UserPO findByName(String name);

    UserPO findById(String userId);
}
