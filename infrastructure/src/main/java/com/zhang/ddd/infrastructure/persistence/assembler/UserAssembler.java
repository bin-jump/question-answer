package com.zhang.ddd.infrastructure.persistence.assembler;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.infrastructure.persistence.po.UserPO;
import org.springframework.beans.BeanUtils;

public class UserAssembler {

    public static UserPO toPO(User user) {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(user, userPO);
        userPO.setVersion(user.getVersion());
        userPO.setDomainId(user.getId());

        return userPO;
    }
}
