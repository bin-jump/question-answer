package com.zhang.ddd.infrastructure.persistence.assembler;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.infrastructure.persistence.po.AnswerPO;
import org.springframework.beans.BeanUtils;

public class AnswerAssembler {

    public static AnswerPO toPO(Answer answer) {
        if (answer == null) {
            return null;
        }
        AnswerPO answerPO = new AnswerPO();
        BeanUtils.copyProperties(answer, answerPO);
        answerPO.setVersion(answer.getVersion());
        answerPO.setAnswerId(answer.getId());

        return answerPO;
    }

    public static Answer toDO(AnswerPO answerPO) {
        if (answerPO == null) {
            return null;
        }
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerPO, answer);
        answer.setId(answerPO.getAnswerId());
        answer.setVersion(answer.getVersion());

        return answer;
    }
}
