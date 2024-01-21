package com.assignment.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Exception {

    public CustomException(String message) {
        super(message);
    }

}
