package com.demo.manageshow.controller;

import com.demo.manageshow.service.ConflictException;
import com.demo.manageshow.service.DuplicateException;
import com.demo.manageshow.service.InvalidException;
import com.demo.manageshow.service.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandlerAdvice {
    @ResponseBody
    @ExceptionHandler({DuplicateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    String handleDuplicateException(DuplicateException ex) {
        return ex.getMessage();

    }

    @ResponseBody
    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    String handleConflictException(ConflictException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({InvalidException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    String handleInvalidException(InvalidException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }
}
