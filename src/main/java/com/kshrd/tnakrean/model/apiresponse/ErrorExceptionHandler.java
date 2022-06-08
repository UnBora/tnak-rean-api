package com.kshrd.tnakrean.model.apiresponse;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorExceptionHandler {


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException e) {
        ResponseError error = ResponseError
                .builder().message(e.getMessage()).details("error").build();

        return ResponseEntity.badRequest()
                .body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleIllegalState(NotFoundException e) {
        ResponseError error = ResponseError
                .builder()
                .details("error")
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
