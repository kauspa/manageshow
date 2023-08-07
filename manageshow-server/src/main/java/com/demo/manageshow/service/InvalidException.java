package com.demo.manageshow.service;

public class InvalidException extends RuntimeException{
    public InvalidException(String message) {
        super(message);
    }
}
