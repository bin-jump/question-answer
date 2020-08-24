package com.zhang.ddd.domain.aggregate.vote.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.Vote;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResourceType;

public interface VoteRepository {

    Vote find(Long voterId, Long resourceId, VoteResourceType resourceType);

    List<Vote> findByResourceIds(Long voterId,
                                 List<Long> resourceIds, VoteResourceType resourceType);

    void save(Vote vote);

    void update(Vote vote);

    void remove(Vote vote);
}
