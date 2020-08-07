package com.zhang.ddd.presentation.facade.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;

public class AnswerAssembler {

    public static AnswerDto toDTO(Answer answer) {
        if (answer == null) {
            return null;
        }
        AnswerDto answerDTO = AnswerDto.builder()
                .id(answer.getId())

                .authorId(answer.getAuthorId())
                .body(answer.getBody())
                .created(answer.getCreated().getTime())
                .commentCount(answer.getCommentCount())
                .parentId(answer.getParentId())
                .upvoteCount(answer.getUpvoteCount())
                .build();

        return answerDTO;
    }

    public static List<AnswerDto> toDTOs(List<Answer> answers) {

        return answers.stream().map(AnswerAssembler::toDTO)
                .collect(Collectors.toList());
    }
}
