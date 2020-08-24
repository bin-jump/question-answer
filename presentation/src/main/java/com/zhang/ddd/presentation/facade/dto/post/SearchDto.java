package com.zhang.ddd.presentation.facade.dto.post;

import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDto {

    private Long id;

    private Long parentId;

    private Float score;

    private String title;

    private String body;

    private String searchType;

    private Long authorId;

    private long created;

    private UserDto author;

    private QuestionDto question;

    private AnswerDto answer;

}
