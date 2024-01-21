package com.assignment.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SendResponse {
    private String message;
    private String error;
}
