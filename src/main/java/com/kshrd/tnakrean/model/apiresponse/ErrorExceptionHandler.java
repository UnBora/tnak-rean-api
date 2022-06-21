package com.kshrd.tnakrean.model.apiresponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

//        List<ErrorMessageResponse> validationErrorDetails = ex.getBindingResult()
//                .getAllErrors()
//                .stream()
//                .map(error -> mapToErrorMessageDto(error))
//                .collect(Collectors.toList());

        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        ErrorResponse response = new ErrorResponse(status.name(), status.value(), errorMap);
        return new ResponseEntity<>(response, status);

    }

//    @ExceptionHandler(TestAntiDuplicate.class)
//    public ApiResponse<Object> handleConstraintViolationException(TestAntiDuplicate ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        Map<String, String> errorMap = new HashMap<>();
//
//        System.err.println("test");
//        ErrorResponse response = new ErrorResponse(status.name(), status.value(), errorMap);
//        return new ApiResponse<>().setData(status);
//    }

}