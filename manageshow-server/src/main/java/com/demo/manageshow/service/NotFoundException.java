package com.demo.manageshow.service;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
