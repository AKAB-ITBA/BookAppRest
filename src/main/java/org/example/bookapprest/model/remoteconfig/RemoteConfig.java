package org.example.bookapprest.model.remoteconfig;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RemoteConfig {
    private Map<String, RemoteConfigParameter> parameters;
    private List<RemoteCondition> conditions;
}


