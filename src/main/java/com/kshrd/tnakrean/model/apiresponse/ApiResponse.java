package com.kshrd.tnakrean.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
public class ApiResponse<T> {
    private T data;
    private String message;
    private HttpStatus status;
}
