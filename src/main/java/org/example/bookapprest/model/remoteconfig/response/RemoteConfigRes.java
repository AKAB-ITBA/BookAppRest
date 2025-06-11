package org.example.bookapprest.model.remoteconfig.response;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RemoteConfigRes {
    private Map<String, RemoteConfigParameterRes> parameters;
    private List<RemoteConditionRes> conditions;
}


