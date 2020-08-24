package com.zhang.ddd.presentation.facade.dto.post;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private Long authorId;

    private long created;

    private int answerCount;

    private int upvoteCount;

    private int followCount;

    private int commentCount;

    private boolean following;

    private boolean upvoted;

    @Singular
    private List<TagDto> tags = new ArrayList<>();

    private UserDto author;

    private AnswerDto cover;

    public QuestionDto copy(){
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(this, questionDto);
        questionDto.setTags(this.tags);
        questionDto.setAuthor(this.author);
        questionDto.setCover(this.cover);

        return questionDto;
    }


}
