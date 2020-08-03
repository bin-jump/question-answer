package com.zhang.ddd.infrastructure.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagPO {

    private Long id;

    private String tagId;

    private long version;

    private String label;
}
