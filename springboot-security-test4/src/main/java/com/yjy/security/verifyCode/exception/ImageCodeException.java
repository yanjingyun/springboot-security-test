package com.yjy.security.verifyCode.exception;

import org.springframework.security.core.AuthenticationException;

public class ImageCodeException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public ImageCodeException(String msg) {
        super(msg);
    }
}
