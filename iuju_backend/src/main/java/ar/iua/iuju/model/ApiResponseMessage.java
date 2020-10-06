package ar.iua.iuju.model;

import io.swagger.annotations.ApiModelProperty;

public class ApiResponseMessage {

    @ApiModelProperty(notes = "api response message")
    private String message;

    public ApiResponseMessage(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}