package com.zhang.ddd.presentation.facade.dto.post;

import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {

    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private String authorId;

    private long created;

    private int answerCount;

    private int voteupCount;

    private int followCount;

    private int commentCount;

    private boolean following;

    private boolean upvoted;

    @Singular
    private List<TagDto> tags = new ArrayList<>();

    private UserDto author;

    private AnswerDTO cover;


}
