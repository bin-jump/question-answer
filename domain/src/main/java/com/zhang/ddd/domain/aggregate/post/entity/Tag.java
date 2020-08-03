package com.zhang.ddd.domain.aggregate.post.entity;

import com.zhang.ddd.domain.exception.InvalidValueException;
import com.zhang.ddd.domain.shared.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class Tag extends Entity<Tag> {

    public static String formatContent(String s) {
        return s.trim()
                .replace(" ", "-")
                .replace("　", "-")
                .toLowerCase();
    }

    public Tag(String id, String label) {
        if (!StringUtils.hasText(label)) {
            throw new InvalidValueException("Tag label can not be empty.");
        }
        label = formatContent(label);

        this.id = id;
        this.label = label;
    }

    private String label;

    public boolean sameContentAs(Tag t) {
        if (t == null) {
            return false;
        }
        return this.label.equals(t.label);
    }



}
