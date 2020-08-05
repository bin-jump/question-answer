package com.zhang.ddd.presentation.facade.assembler;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;

import java.util.stream.Collectors;

public class QuestionAssembler {

    public static QuestionDto toDTO(Question question, UserDto userDto) {
        if (question == null) {
            return null;
        }
        QuestionDto questionDto = QuestionDto.builder()
                .id(question.getId())
                .title(question.getTitle())
                .authorId(question.getAuthorId())
                .body(question.getBody())
                .created(question.getCreated().getTime())
                .answerCount(question.getAnswerCount())
                .commentCount(question.getCommentCount())
                .followCount(question.getFollowCount())
                .upvoteCount(question.getUpvoteCount())
                .build();

        questionDto.setTags(question.getTags()
                .stream().map(TagAssembler::toDTO).collect(Collectors.toList()));

        if (userDto != null) {
            questionDto.setAuthor(userDto);
        }

        return questionDto;
    }
}
