package com.zhang.ddd.presentation.facade;

import com.zhang.ddd.application.service.QuestionApplicationService;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.CommentRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.Vote;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResourceType;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteType;
import com.zhang.ddd.domain.aggregate.vote.repository.VoteRepository;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.presentation.facade.assembler.AnswerAssembler;
import com.zhang.ddd.presentation.facade.assembler.CommentAssembler;
import com.zhang.ddd.presentation.facade.assembler.QuestionAssembler;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.CommentDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.web.security.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    @Autowired
    VoteRepository voteRepository;

    public QuestionDto createQuestion(String title, String body, String authorId, List<String> tagLabels) {

        Question question = questionApplicationService.create(title, body, authorId, tagLabels);
        return QuestionAssembler.toDTO(question, null, false, false);
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
                            QuestionAssembler.toDTO(e, UserAssembler.toDTO(
                                    questionUsers.get(e.getAuthorId())),
                                    false,
                                    false);

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

    // TODO: add follow check
    public QuestionDto getQuestion(String id) {
        Question question = questionRepository.findById(id);
        if (question == null) {
            throw new ResourceNotFoundException("Question not found");
        }
        User user = userRepository.findById(question.getAuthorId());

        QuestionDto questionDto =
                QuestionAssembler.toDTO(question, UserAssembler.toDTO(user), false, false);

        fillQuestionVotes(Arrays.asList(questionDto));

        return questionDto;
    }

    public AnswerDto createAnswer(String questionId, String body, String authorId, UserDto user) {

        Answer answer = questionApplicationService.createAnswer(questionId, body, authorId);
        return AnswerAssembler.toDTO(answer, user, false, false);
    }

    public AnswerDto getAnswer(String id) {
        Answer answer = answerRepository.findById(id);
        if (answer == null) {
            throw new ResourceNotFoundException("Answer not found");
        }

        User user = userRepository.findById(answer.getAuthorId());
        AnswerDto answerDto =
                AnswerAssembler.toDTO(answer, UserAssembler.toDTO(user), false, false);

        fillAnswerVotes(Arrays.asList(answerDto));

        return answerDto;
    }

    // TODO: add vote check
    public List<AnswerDto> getQuestionAnswers(String questionId, String cursor, int size) {
        List<Answer> answers = answerRepository
                .findByQuestionId(questionId, new PostPaging(cursor, size));
        Map<String, User> answerUsers = userRepository.findByIds(answers.stream()
                .map(Answer::getAuthorId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(User::getId, e -> e));

        List<AnswerDto> res = answers.stream()
                .map(e -> AnswerAssembler.toDTO(e, UserAssembler.toDTO(answerUsers.get(e.getAuthorId())),
                        false, false))
                        .collect(Collectors.toList());

        fillAnswerVotes(res);
        return res;
    }

    public CommentDto addQuestionComment(String authorId, String questionId, String body, UserDto user) {

        Comment comment = questionApplicationService.addQuestionComment(authorId, questionId, body);
        return CommentAssembler.toDTO(comment, user);
    }

    public CommentDto addAnswerComment(String authorId, String questionId, String body, UserDto user) {

        Comment comment = questionApplicationService.addAnswerComment(authorId, questionId, body);
        return CommentAssembler.toDTO(comment, user);
    }


    public List<CommentDto> getQuestionComments(String questionId, String cursor, int size) {

        return getComments(CommentResourceType.QUESTION, questionId, cursor, size);
    }

    public List<CommentDto> getAnswerComments(String answerId, String cursor, int size) {

        return getComments(CommentResourceType.ANSWER, answerId, cursor, size);
    }


    private List<CommentDto> getComments(CommentResourceType resourceType, String resourceId,
                                         String cursor, int size) {

        List<Comment> comments = commentRepository.findByResourceId(resourceId,
                resourceType, new PostPaging(cursor, size));
        Map<String, UserDto> users = userRepository.findByIds(comments.stream()
                .map(Comment::getAuthorId).collect(Collectors.toList()))
                .stream()
                .map(UserAssembler::toDTO)
                .collect(Collectors.toMap(UserDto::getId, e -> e));

        List<CommentDto> res = comments.stream()
                .map(e ->  CommentAssembler.toDTO(e, users.get(e.getAuthorId())))
                .collect(Collectors.toList());

        return res;
    }

    private void fillQuestionVotes(List<QuestionDto> questionDtos) {
        UserDto me = LoginUtil.getCurrentUser();
        if (me == null){
            return;
        }
        Map<String, QuestionDto> qm = questionDtos
                .stream().collect(Collectors.toMap(QuestionDto::getId, e -> e));

        List<Vote> votes = voteRepository.findByResourceIds(me.getId(),
                questionDtos.stream().map(QuestionDto::getId).collect(Collectors.toList()),
                VoteResourceType.QUESTION);

        votes.stream().forEach(e -> {
            QuestionDto questionDto = qm.get(e.getResourceId());
            questionDto.setUpvoted(e.getVoteType() == VoteType.UPVOTE);
        });
    }

    private void fillAnswerVotes(List<AnswerDto> answerDtos) {
        UserDto me = LoginUtil.getCurrentUser();
        if (me == null){
            return;
        }
        Map<String, AnswerDto> am = answerDtos
                .stream().collect(Collectors.toMap(AnswerDto::getId, e -> e));

        List<Vote> votes = voteRepository.findByResourceIds(me.getId(),
                answerDtos.stream().map(AnswerDto::getId).collect(Collectors.toList()),
                VoteResourceType.ANSWER);

        votes.stream().forEach(e -> {
            AnswerDto answerDto = am.get(e.getResourceId());
            answerDto.setUpvoted(e.getVoteType() == VoteType.UPVOTE);
            answerDto.setDownvoted(e.getVoteType() == VoteType.DOWNVOTE);
        });
    }
}
