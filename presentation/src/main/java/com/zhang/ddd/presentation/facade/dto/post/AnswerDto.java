package com.zhang.ddd.presentation.facade.dto.post;

import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {


    private Long id;

    @NotBlank
    private Long parentId;

    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private Long authorId;

    private long created;

    private int upvoteCount;

    private int commentCount;

    private UserDto author;

    private boolean upvoted;

    private boolean downvoted;

}
