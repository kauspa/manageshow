package com.demo.manageshow.service;

public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
