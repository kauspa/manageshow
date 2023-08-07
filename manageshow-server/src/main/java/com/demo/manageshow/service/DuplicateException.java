package com.demo.manageshow.service;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message){
        super(message);
    }
}
