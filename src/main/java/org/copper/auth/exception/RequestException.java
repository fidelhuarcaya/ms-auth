package org.copper.auth.exception;

import lombok.Getter;

@Getter
public class RequestException extends RuntimeException {
    private final String message;

    public RequestException(String message) {
        super(message);
        this.message = message;
    }

}
