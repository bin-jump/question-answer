package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.Vote;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResourceType;
import com.zhang.ddd.domain.aggregate.vote.repository.VoteRepository;
import com.zhang.ddd.domain.exception.InvalidOperationException;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.infrastructure.persistence.assembler.VoteAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.VoteMapper;
import com.zhang.ddd.infrastructure.persistence.po.VotePO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.awt.font.NumericShaper;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    @Autowired
    VoteMapper voteMapper;

    @Override
    public Vote find(Long voterId, Long resourceId, VoteResourceType resourceType) {

        VotePO votePO = voteMapper.find(voterId, resourceId, resourceType);
        return VoteAssembler.toDO(votePO);
    }

    @Override
    public List<Vote> findByResourceIds(Long voterId, List<Long> resourceIds,
                                        VoteResourceType resourceType) {

        List<VotePO> votePOs = voteMapper.findByResourceIds(voterId, resourceIds, resourceType);
        return votePOs.stream().map(VoteAssembler::toDO).collect(Collectors.toList());
    }

    @Override
    public void save(Vote vote) {
        VotePO votePO = VoteAssembler.toPO(vote);
        int num = voteMapper.insert(votePO);

    }

    @Override
    public void update(Vote vote) {
        VotePO votePO = VoteAssembler.toPO(vote);
        int num = voteMapper.update(votePO);
        if (num == 0) {
            //throw new InvalidOperationException("Wrong vote operation.");
        }
    }

    @Override
    public void remove(Vote vote) {
        VotePO votePO = VoteAssembler.toPO(vote);
        int num = voteMapper.delete(votePO);
        if (num == 0) {
            //throw new InvalidOperationException("Wrong vote operation.");
        }
    }
}
