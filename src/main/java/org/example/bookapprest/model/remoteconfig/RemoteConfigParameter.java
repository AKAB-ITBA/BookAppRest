package org.example.bookapprest.model.remoteconfig;

import lombok.Data;

@Data
public class RemoteConfigParameter {
    private DefaultValue defaultValue;
    private String description;
}
