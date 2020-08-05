package com.zhang.ddd.presentation.web.security;

import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.web.security.WebUserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUtil {

    public static UserDto getCurrentUser() {

        WebUserPrincipal user = (WebUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return UserAssembler.toDTO(user.getUser());
    }
}
