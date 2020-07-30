package com.zhang.ddd.web.dto.message;

import java.util.List;
import com.zhang.ddd.web.dto.user.UserDto;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDto {

    private String id;

    private String withId;

    private UserDto withUser;

    private String coverText;

    private long lastTime;

    private int unreadCount;

    @Singular
    private List<MessageDto> messages = new ArrayList<>();

}
