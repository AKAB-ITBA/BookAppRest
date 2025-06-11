package org.example.bookapprest.model.remoteconfig.request;

import lombok.Data;

@Data
public class RemoteConditionReq {
    private String name;
    private String expression;
    private String tagColor;
}
