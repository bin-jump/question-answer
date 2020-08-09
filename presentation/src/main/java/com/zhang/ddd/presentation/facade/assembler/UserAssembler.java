package com.zhang.ddd.presentation.facade.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.entity.valueobject.UserGender;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;

public class UserAssembler {

    public static UserDto toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                //.password(user.getPassword())
                .avatarUrl(user.getAvatarUrl())
                .description(user.getDescription())
                .created(user.getCreated().getTime())
                .email(user.getEmail())
                .gender(user.getGender().toString())
                .age(user.getAge())
                .answerCount(user.getAnswerCount())
                .followCount(user.getFollowCount())
                .followerCount(user.getFollowerCount())
                .followeeCount(user.getFolloweeCount())
                .questionCount(user.getQuestionCount())
                .build();

        return userDto;
    }

    public static List<UserDto> toDTOs(List<User> users) {
        return users.stream().map(UserAssembler::toDTO).collect(Collectors.toList());
    }

}
