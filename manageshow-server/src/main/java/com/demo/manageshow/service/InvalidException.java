package com.demo.manageshow.service;

public class InvalidException extends RuntimeException {
    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(String message, Throwable ex) {
        super(message, ex);
    }

}
