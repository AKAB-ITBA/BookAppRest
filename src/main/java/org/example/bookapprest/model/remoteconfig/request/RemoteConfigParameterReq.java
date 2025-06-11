package org.example.bookapprest.model.remoteconfig.request;

import lombok.Data;

@Data
public class RemoteConfigParameterReq {
    private DefaultValueReq defaultValueReq;
    private String description;
}
