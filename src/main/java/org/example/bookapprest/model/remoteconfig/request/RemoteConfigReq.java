package org.example.bookapprest.model.remoteconfig.request;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RemoteConfigReq {
    private Map<String, RemoteConfigParameterReq> parameters;
    private List<RemoteConditionReq> conditions;
}


