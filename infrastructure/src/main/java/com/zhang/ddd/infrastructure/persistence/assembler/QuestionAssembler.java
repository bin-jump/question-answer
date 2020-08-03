package com.zhang.ddd.infrastructure.persistence.assembler;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.infrastructure.persistence.po.QuestionPO;
import com.zhang.ddd.infrastructure.persistence.po.TagPO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;

public class QuestionAssembler {

    public static QuestionPO toPO(Question question) {
        if (question == null) {
            return null;
        }
        QuestionPO questionPO = new QuestionPO();
        BeanUtils.copyProperties(question, questionPO);
        questionPO.setTags(new ArrayList<>());
        questionPO.setVersion(question.getVersion());
        questionPO.setQuestionId(question.getId());

        for (Tag t : question.getTags()) {
            questionPO.getTags().add(TagAssembler.toPO(t));
        }

        return questionPO;
    }

    public static Question toDO(QuestionPO questionPO) {
        if (questionPO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionPO, question);
        question.setTags(new ArrayList<>());
        question.setId(questionPO.getQuestionId());
        question.setVersion(questionPO.getVersion());

        for (TagPO t : questionPO.getTags()) {
            question.getTags().add(TagAssembler.toDO(t));
        }

        return question;
    }
}
