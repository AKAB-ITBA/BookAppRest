package org.example.bookapprest.model.rest;

import lombok.Data;

import java.util.Map;

@Data
public class RestResponse<T> {
    private T object;
    private int code;
    private boolean success;
    private String message;

    public RestResponse() {
    }


    public static <T> RestResponse<T> ok(T object) {
        RestResponse<T> restResponse = ok();
        restResponse.object = object;
        return restResponse;
    }

    public static <T> RestResponse<T> ok() {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = 200;
        restResponse.success = true;
        restResponse.message = "success";
        return restResponse;
    }

    public static <T> RestResponse<T> error() {
        RestResponse<T> restResponse = new RestResponse<>();
        restResponse.code = 404;
        restResponse.success = false;
        restResponse.message = "error";
        return restResponse;
    }

}
