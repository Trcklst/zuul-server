package com.trklst.zuulserver.security.excpetions;

import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;

public class NoTokenSendException extends ZuulException {

    public NoTokenSendException() {
        super("No Token has been sent", HttpStatus.BAD_REQUEST.value(), null);
    }
}
