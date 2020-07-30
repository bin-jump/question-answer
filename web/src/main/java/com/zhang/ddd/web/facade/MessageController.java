package com.zhang.ddd.web.facade;

import java.util.List;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.web.dto.user.UserDto;
import com.zhang.ddd.web.dto.message.ChatDto;
import com.zhang.ddd.web.dto.message.MessageDto;
import com.zhang.ddd.web.dto.message.SendMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/api/message")
public class MessageController {

    @GetMapping
    public Response getChats() {


        UserDto you = UserDto.builder()
                .id("abc456")
                .name("zhang")
                .email("user@example.com")
                .description("I am a user.")
                .created(1429070000)
                .gender("")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F807.jpg?s=400&d=identicon")
                .build();

        ChatDto chat = ChatDto.builder()
                .id("ccc111")
                .withUser(you)
                .withId(you.getId())
                .lastTime(1429070000)
                .unreadCount(3)
                .coverText("This is a message.")
                .build();
        List<ChatDto> res = new ArrayList<>();
        UserDto u1 = new UserDto();
        UserDto u2 = new UserDto();
        BeanUtils.copyProperties(you, u1);
        BeanUtils.copyProperties(you, u2);
        u1.setId("uuu111");
        u2.setId("uuu222");
        u1.setName("user1");
        u2.setName("user2");

        ChatDto c1 = new ChatDto();
        ChatDto c2 = new ChatDto();
        BeanUtils.copyProperties(chat, c1);
        BeanUtils.copyProperties(chat, c2);

        c1.setId("ccc222");
        c2.setId("ccc333");
        c1.setWithId(u1.getId());
        c1.setWithUser(u1);
        c2.setWithId(u2.getId());
        c2.setWithUser(u2);

        res.add(chat);
        res.add(c1);
        res.add(c2);

        return Response.okPagingAfter(res, chat.getId(), 3);
    }

    @GetMapping("{id}")
    public Response getChat(@PathVariable String id) {

        UserDto me = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .email("user@example.com")
                .description("I am a user.")
                .created(1429070000)
                .gender("")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F807.jpg?s=400&d=identicon")
                .build();
        UserDto u1 = new UserDto();
        BeanUtils.copyProperties(me, u1);
        u1.setId("uuu111");
        u1.setName("user1");
        u1.setAvatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F897.jpg?s=400&d=identicon");


        MessageDto msg1 = MessageDto.builder()
                .body("This is a message from me.")
                .created(1429170000)
                .fromMe(true)
                .id("mmm111")
                .build();
        MessageDto msg2 = MessageDto.builder()
                .body("Hi. This is a message from User.")
                .created(1429170000)
                .fromMe(false)
                .id("mmm222")
                .build();
        MessageDto msg3 = MessageDto.builder()
                .body("This is a message from me.")
                .created(1429170000)
                .fromMe(true)
                .id("mmm333")
                .build();

        List<MessageDto> res = new ArrayList<>();

        res.add(msg1);
        res.add(msg2);
        res.add(msg3);

        return Response.okPagingAfter(res, msg3.getId(), 3);
    }


    @PostMapping("{id}")
    public Response getChat(@PathVariable String id, @RequestBody @Valid SendMessageRequest request) {
        UserDto you = UserDto.builder()
                .id(id)
                .name("sender-"+id)
                .email("user@example.com")
                .description("I am a sender.")
                .created(1429070000)
                .gender("")
                .avatarUrl("https://www.gravatar.com/avatar/AABCE5C74F589F4867DBD57E9CA9F807.jpg?s=400&d=identicon")
                .build();

        MessageDto msg = MessageDto.builder()
                .body(request.getBody())
                .created(1429170000)
                .fromMe(true)
                .id("mmm888")
                .build();
        ChatDto chat = ChatDto.builder()
                .id("cccsend111")
                .withUser(you)
                .withId(you.getId())
                .lastTime(1429070000)
                .unreadCount(1)
                .coverText("This is a message.")
                .message(msg)
                .build();

        return Response.ok(chat);
    }

    @GetMapping("read")
    public Response checkUnreadChats() {

        UserDto you = UserDto.builder()
                .id("abc456")
                .name("zhang")
                .email("user@example.com")
                .description("I am a user.")
                .created(1429070000)
                .gender("")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F807.jpg?s=400&d=identicon")
                .build();

        ChatDto chat = ChatDto.builder()
                .id("ccc111")
                .withUser(you)
                .withId(you.getId())
                .lastTime(1429070000)
                .unreadCount(3)
                .coverText("This is a message.")
                .build();
        List<ChatDto> res = new ArrayList<>();
        UserDto u3 = new UserDto();
        BeanUtils.copyProperties(you, u3);
        u3.setId("uuu333");
        u3.setName("user3");

        ChatDto c2 = new ChatDto();
        BeanUtils.copyProperties(chat, c2);

        c2.setId("ccc444");
        c2.setWithId(u3.getId());
        c2.setWithUser(u3);

        res.add(chat);
        res.add(c2);

        return Response.okPagingAfter(res, null, 0);
    }

    @PutMapping("{id}/read")
    public Response readChat(@PathVariable String id) {



        MessageDto msg2 = MessageDto.builder()
                .body("New, hi, This is a message from User new new new.")
                .created(1429170000)
                .fromMe(false)
                .id("mmm888")
                .build();
        MessageDto msg3 = MessageDto.builder()
                .body("New, this is a message from me  new new new.")
                .created(1429170000)
                .fromMe(false)
                .id("mmm999")
                .build();

        List<MessageDto> res = new ArrayList<>();

        res.add(msg2);
        res.add(msg3);

        return Response.okPagingAfter(res, null, 0);
    }
}
