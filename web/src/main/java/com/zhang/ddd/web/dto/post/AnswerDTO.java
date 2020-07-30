package com.zhang.ddd.web.dto.post;

import com.zhang.ddd.web.dto.user.UserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDTO {


    private String id;

    @NotBlank
    private String parentId;

    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private String authorId;

    private long created;

    private int upvoteCount;

    private int commentCount;

    private UserDto author;

    private boolean upvoted;

    private boolean downvoted;

}
