package com.zhang.ddd.infrastructure.persistence.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.infrastructure.persistence.po.CommentPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

public class CommentAssembler {

    public static CommentPO toPO(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentPO commentPO = new CommentPO();
        BeanUtils.copyProperties(comment, commentPO);
        commentPO.setVersion(comment.getVersion());
        commentPO.setId(NumberEncoder.decode(comment.getId()));
        commentPO.setAuthorId(NumberEncoder.decode(comment.getAuthorId()));

        return commentPO;
    }

    public static List<Comment> toDOs(List<CommentPO> commentPOs) {

        return commentPOs.stream()
                .map(CommentAssembler::toDO).collect(Collectors.toList());
    }


    public static Comment toDO(CommentPO commentPO) {
        if (commentPO == null) {
            return null;
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentPO, comment);
        comment.setId(NumberEncoder.encode(commentPO.getId()));
        comment.setAuthorId(NumberEncoder.encode(commentPO.getAuthorId()));

        comment.setVersion(commentPO.getVersion());

        return comment;
    }
}
