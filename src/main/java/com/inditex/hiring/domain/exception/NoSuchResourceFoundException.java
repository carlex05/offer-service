package com.inditex.hiring.domain.exception;

public class NoSuchResourceFoundException extends RuntimeException {

 
	private static final long serialVersionUID = -8749643454264131447L;

	public NoSuchResourceFoundException(String msg) {
        super(msg);
    }
}
