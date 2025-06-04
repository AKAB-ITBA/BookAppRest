package org.example.bookapprest.model.remoteconfig;

import lombok.Data;

@Data
public class RemoteCondition {
    private String name;
    private String expression;
    private String tagColor;
}
