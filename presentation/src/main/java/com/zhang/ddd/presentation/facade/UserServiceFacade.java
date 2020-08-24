package com.zhang.ddd.presentation.facade;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.zhang.ddd.application.service.UserApplicationService;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.repository.FavorPaging;
import com.zhang.ddd.domain.aggregate.favor.repository.FollowRepository;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.entity.valueobject.UserGender;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.aggregate.user.service.command.ChangeAvatarCommand;
import com.zhang.ddd.domain.aggregate.user.service.command.EditUserCommand;
import com.zhang.ddd.presentation.facade.assembler.AnswerAssembler;
import com.zhang.ddd.presentation.facade.assembler.QuestionAssembler;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.web.security.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceFacade {

    @Autowired
    UserApplicationService userApplicationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    FacadeHelper helper;

    public UserDto create(UserDto userDto) {

        User user = userApplicationService.create(userDto.getName(), userDto.getPassword(), userDto.getEmail());
        return UserAssembler.toDTO(user);
    }

    public UserDto edit(UserDto userDto) {

        EditUserCommand editUserCommand = EditUserCommand.builder()
                .id(userDto.getId())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .gender(UserGender.valueOf(userDto.getGender()))
                .description(userDto.getDescription())
                .build();

        User user = userApplicationService.edit(editUserCommand);
        return UserAssembler.toDTO(user);
    }

    public UserDto changeAvatarImage(Long id, MultipartFile image) throws IOException {
        ChangeAvatarCommand command = ChangeAvatarCommand.builder()
                .userId(id)
                .fileName(image.getOriginalFilename())
                .fileSize(image.getSize())
                .stream(image.getInputStream())
                .build();
        User user = userApplicationService.changeAvatarImage(command);
        return  UserAssembler.toDTO(user);
    }

    public void changePassword(Long id, String oldPassword, String newPassword) {
        userApplicationService.changePassword(id, oldPassword, newPassword);
    }

    public UserDto findByName(String name) {
        User user = userRepository.findByName(name);
        return UserAssembler.toDTO(user);
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id);
        UserDto userDto = UserAssembler.toDTO(user);

        UserDto me = LoginUtil.getCurrentUser();
        if (user != null && user.getId() != me.getId()) {
            Follow follow = followRepository.find(me.getId(), userDto.getId(),
                    FollowResourceType.USER);
            userDto.setFollowing(follow != null ? true : false);
        }
        return userDto;
    }

    public List<QuestionDto> findUserQuestions(Long userId, Long cursor, int size) {

        List<QuestionDto> questionDtos = QuestionAssembler.toDTOs(questionRepository
                .findByUserId(userId, new PostPaging(cursor, size)));

        helper.fillQuestionUsers(questionDtos);
        return questionDtos;
    }

    public List<QuestionDto> findUserAnswers(Long userId, Long cursor, int size) {

        List<AnswerDto> answerDtos = answerRepository.findByUserId(userId, new PostPaging(cursor, size))
                .stream().map(e -> AnswerAssembler.toDTO(e))
                .collect(Collectors.toList());

        helper.fillAnswerUsers(answerDtos);

        return helper.wrapAnswerQuestion(answerDtos);
    }

    public List<QuestionDto> findfollowedQuestions(Long userId, Long cursor, int size) {

        List<Long> questionIds = followRepository.findFollowed(userId, FollowResourceType.QUESTION,
                new FavorPaging(cursor, size))
                .stream().map(Follow::getResourceId).collect(Collectors.toList());

        List<Question> questions = questionRepository.findByIds(questionIds);
        List<QuestionDto> questionDtos = QuestionAssembler.toDTOs(questions);

        helper.fillQuestionUsers(questionDtos);
        return questionDtos;
    }

    public List<UserDto> findfollower(Long userId, Long cursor, int size) {

        List<Long> userIds = followRepository.findFollower(userId, FollowResourceType.USER,
                new FavorPaging(cursor, size))
                .stream().map(Follow::getFollowerId).collect(Collectors.toList());

        List<UserDto> userDtos = userRepository.findByIds(userIds)
               .stream().map(UserAssembler::toDTO).collect(Collectors.toList());
        return userDtos;
    }

    public List<UserDto> findfollowee(Long userId, Long cursor, int size) {

        List<Long> userIds = followRepository.findFollowed(userId, FollowResourceType.USER,
                new FavorPaging(cursor, size))
                .stream().map(Follow::getResourceId).collect(Collectors.toList());

        List<UserDto> userDtos = userRepository.findByIds(userIds)
                .stream().map(UserAssembler::toDTO).collect(Collectors.toList());
        return userDtos;
    }

}
