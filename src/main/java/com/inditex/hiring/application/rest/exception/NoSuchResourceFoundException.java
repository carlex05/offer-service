package com.inditex.hiring.application.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchResourceFoundException extends RuntimeException {

 
	private static final long serialVersionUID = -8749643454264131447L;

	public NoSuchResourceFoundException(String msg) {
        super(msg);
    }
}
