package com.zhang.ddd.infrastructure.persistence.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.infrastructure.persistence.po.TagPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

public class TagAssembler {

    public static TagPO toPO(Tag tag) {
        if (tag == null) {
            return null;
        }
        TagPO tagPO = new TagPO();
        tagPO.setId(tag.getId());
        tagPO.setLabel(tag.getLabel());
        tagPO.setVersion(tag.getVersion());

        return tagPO;
    }

    public static Tag toDO(TagPO tagPO) {
        if (tagPO == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setId(tagPO.getId());
        tag.setLabel(tagPO.getLabel());
        tag.setVersion(tagPO.getVersion());

        return tag;
    }

    public static List<TagPO> toPOs(List<Tag> tags) {

        return tags.stream().map(t -> toPO(t)).collect(Collectors.toList());
    }

    public static List<Tag> toDOs(List<TagPO> tagPOs) {

        return tagPOs.stream().map(t -> toDO(t)).collect(Collectors.toList());
    }
}
