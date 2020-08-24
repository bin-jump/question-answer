package com.zhang.ddd.infrastructure.persistence.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.Vote;
import com.zhang.ddd.infrastructure.persistence.po.VotePO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

public class VoteAssembler {

    public static VotePO toPO(Vote vote) {
        if (vote == null) {
            return null;
        }
        VotePO votePO = new VotePO();
        BeanUtils.copyProperties(vote, votePO);

        return votePO;
    }

    public List<Vote> toDOs(List<VotePO> votePOs) {
        return votePOs.stream()
                .map(VoteAssembler::toDO).collect(Collectors.toList());
    }

    public static Vote toDO(VotePO votePO) {
        if (votePO == null) {
            return null;
        }
        Vote vote = new Vote();
        BeanUtils.copyProperties(votePO, vote);

        return vote;
    }
}
