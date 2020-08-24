package com.zhang.ddd.infrastructure.persistence.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.infrastructure.persistence.po.AnswerPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

public class AnswerAssembler {

    public static AnswerPO toPO(Answer answer) {
        if (answer == null) {
            return null;
        }
        AnswerPO answerPO = new AnswerPO();
        BeanUtils.copyProperties(answer, answerPO);
        answerPO.setVersion(answer.getVersion());

        return answerPO;
    }

    public static List<Answer> toDOs(List<AnswerPO> answerPOs) {

        return answerPOs.stream()
                .map(AnswerAssembler::toDO).collect(Collectors.toList());
    }

    public static Answer toDO(AnswerPO answerPO) {
        if (answerPO == null) {
            return null;
        }
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerPO, answer);
        answer.setVersion(answer.getVersion());

        return answer;
    }
}
