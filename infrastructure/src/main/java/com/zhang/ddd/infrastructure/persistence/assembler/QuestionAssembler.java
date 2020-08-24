package com.zhang.ddd.infrastructure.persistence.assembler;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.infrastructure.persistence.po.QuestionPO;
import com.zhang.ddd.infrastructure.persistence.po.TagPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class QuestionAssembler {

    public static QuestionPO toPO(Question question) {
        if (question == null) {
            return null;
        }
        QuestionPO questionPO = new QuestionPO();
        BeanUtils.copyProperties(question, questionPO);
        questionPO.setTags(new ArrayList<>());
        questionPO.setVersion(question.getVersion());

        for (Tag t : question.getTags()) {
            questionPO.getTags().add(TagAssembler.toPO(t));
        }

        return questionPO;
    }

    public static List<Question> toDOs(List<QuestionPO> questionPOs) {

        return questionPOs.stream()
                .map(QuestionAssembler::toDO).collect(Collectors.toList());
    }

    public static Question toDO(QuestionPO questionPO) {
        if (questionPO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionPO, question);
        question.setTags(new ArrayList<>());
        question.setVersion(questionPO.getVersion());

        for (TagPO t : questionPO.getTags()) {
            question.getTags().add(TagAssembler.toDO(t));
        }

        return question;
    }
}
