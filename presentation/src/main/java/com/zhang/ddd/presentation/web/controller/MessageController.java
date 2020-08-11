package com.zhang.ddd.presentation.web.controller;

import java.util.List;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.MessageServiceFacade;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.facade.dto.message.ChatDto;
import com.zhang.ddd.presentation.facade.dto.message.MessageDto;
import com.zhang.ddd.presentation.facade.dto.message.SendMessageRequest;
import com.zhang.ddd.presentation.web.security.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageServiceFacade messageServiceFacade;

    @GetMapping
    public Response getChats(@RequestParam(required = false) String after,
                             @RequestParam(defaultValue = "10") int size) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        List<ChatDto> chatDtos = messageServiceFacade.getUserChats(currentUser, after, size);
        String next = chatDtos.size() > 0 ? chatDtos.get(chatDtos.size() - 1).getId() : null;

        return Response.okPagingAfter(chatDtos, next, size);
    }

    @GetMapping("{id}")
    public Response getMessages(@PathVariable String id,
                            @RequestParam(required = false) String after,
                            @RequestParam(defaultValue = "10") int size) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        List<MessageDto> messageDtos = messageServiceFacade.getUserMessage(currentUser, id, after, size);
        String next = messageDtos.size() > 0 ? messageDtos.get(messageDtos.size() - 1).getId() : null;

        return Response.okPagingAfter(messageDtos, next, size);
    }


    @PostMapping("{id}")
    public Response sendMessage(@PathVariable String id, @RequestBody @Valid SendMessageRequest request) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        ChatDto chatDto = messageServiceFacade.sendMessage(currentUser.getId(), id, request.getBody());

        return Response.ok(chatDto);
    }

    @GetMapping("read")
    public Response checkUnreadChats() {

        UserDto currentUser = LoginUtil.getCurrentUser();
        List<ChatDto> chatDtos = messageServiceFacade.getUserChats(currentUser, null, 50);

        return Response.okPagingAfter(chatDtos, null, 0);
    }

    @PutMapping("{id}/read")
    public Response readNewMessage(@PathVariable String id, @RequestParam String lastId) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        List<MessageDto> messageDtos = messageServiceFacade.getUserNewMessage(currentUser, id, lastId, 300);

        return Response.okPagingAfter(messageDtos, null, 0);
    }
}
