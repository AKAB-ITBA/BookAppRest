package org.example.bookapprest.model.remoteconfig.request;

import lombok.Data;

@Data
public class DefaultValueReq {
    private Integer subcategoryID;
    private String subcategoryName;
    private String titleAz;
    private String titleEn;
    private String titleRu;
}
