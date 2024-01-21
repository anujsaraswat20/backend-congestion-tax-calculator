package com.assignment.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Getter
@Setter
public class CustomRunTimeException extends ResponseStatusException {

    public CustomRunTimeException(HttpStatusCode status, String message) {
        super(status, message);
    }

    public CustomRunTimeException(HttpStatusCode status) {
        super(status);
    }
}
