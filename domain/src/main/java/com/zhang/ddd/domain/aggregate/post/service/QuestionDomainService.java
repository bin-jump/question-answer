package com.zhang.ddd.domain.aggregate.post.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.post.repository.TagRepository;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.exception.InvalidValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionDomainService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TagRepository tagRepository;

    public Question create(String title, String body, String authorId, List<String> tagLables) {
        if (tagLables.size() == Question.TAG_NUM_LIMIT) {
            throw new InvalidValueException("Too many question tag.");
        }

        String id = questionRepository.nextId();
        Question question = new Question(id, title, body, authorId);

        tagLables = tagLables.stream().map(e -> Tag.formatContent(e)).collect(Collectors.toList());

        // check existed labels
        List<Tag> existedTags = tagRepository.findByLabels(tagLables);

        List<String> existedTagLabels = existedTags
                .stream().map(t -> t.getLabel()).collect(Collectors.toList());
        List<String> newLabels = tagLables.stream()
                .filter(e -> !existedTagLabels.contains(e))
                .collect(Collectors.toList());

        // create new tags
        List<Tag> newTags = newLabels
                .stream().map(e -> new Tag(tagRepository.nextId(), e)).collect(Collectors.toList());

        // add existed tags
        existedTags.stream().forEach(e -> question.addTag(e));

        // add and save new tags
        newTags.stream().forEach(e -> {
            tagRepository.save(e);
            question.addTag(e);
        });

        questionRepository.save(question);
        return question;
    }
}
