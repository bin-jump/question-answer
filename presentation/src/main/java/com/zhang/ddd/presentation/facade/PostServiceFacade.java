package com.zhang.ddd.presentation.facade;

import com.zhang.ddd.application.service.QuestionApplicationService;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.CommentRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.presentation.facade.assembler.AnswerAssembler;
import com.zhang.ddd.presentation.facade.assembler.QuestionAssembler;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostServiceFacade {

    @Autowired
    QuestionApplicationService questionApplicationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    CommentRepository commentRepository;

    public QuestionDto createQuestion(String title, String body, String authorId, List<String> tagLabels) {
        Question question = questionApplicationService.create(title, body, authorId, tagLabels);

        return QuestionAssembler.toDTO(question, null);

    }

    public List<QuestionDto> getQuestions(String cursor, int size) {

        List<Question> questions = questionRepository.findQuestions(new PostPaging(cursor, size));
        // get question authors
        Map<String, User> questionUsers = userRepository.findByIds(questions.stream()
                .map(Question::getAuthorId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(User::getId, e -> e));
        // get cover answers
        Map<String, Answer> coverAnswers = answerRepository.findQuestionLatestAnswers(questions.stream()
                .map(Question::getId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Answer::getParentId, e -> e));
        // get cover answer authors
        Map<String, User> answerUsers = userRepository
                .findByIds(coverAnswers.values().stream().map(Answer::getAuthorId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(User::getId, e -> e));


        List<QuestionDto> questionDtos = questions.stream()
                .map(e ->{
                    QuestionDto questionDto =
                            QuestionAssembler.toDTO(e, UserAssembler.toDTO(questionUsers.get(e.getAuthorId())));

                    if (coverAnswers.containsKey(questionDto.getId())) {
                        Answer coverAnswer = coverAnswers.get(questionDto.getId());
                        AnswerDto answerDto = AnswerAssembler.toDTO(coverAnswer
                        , UserAssembler.toDTO(answerUsers.get(coverAnswer.getAuthorId()))
                        , false, false);
                        questionDto.setCover(answerDto);
                    }
                    return questionDto;

                }).collect(Collectors.toList());

        return questionDtos;
    }

}
