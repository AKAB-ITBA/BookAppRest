package org.example.bookapprest.model.remoteconfig.response;

import lombok.Data;

@Data
public class RemoteConfigParameterRes {
    private DefaultValueRes defaultValueRes;
    private String description;
}
