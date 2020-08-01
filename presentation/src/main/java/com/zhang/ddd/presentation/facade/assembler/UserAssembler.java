package com.zhang.ddd.presentation.facade.assembler;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.entity.valueobject.UserGender;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;

public class UserAssembler {

    public static UserDto toDTO(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .avatarUrl(user.getAvatarUrl())
                .description(user.getDescription())
                .created(user.getCreated().getTime())
                .email(user.getEmail())
                .gender(user.getGender().toString())
                .age(user.getAge())
                .answerCount(user.getAnswerCount())
                .followerCount(user.getFollowerCount())
                .followeeCount(user.getFolloweeCount())
                .questionCount(user.getQuestionCount())
                .build();

        return userDto;
    }

}
