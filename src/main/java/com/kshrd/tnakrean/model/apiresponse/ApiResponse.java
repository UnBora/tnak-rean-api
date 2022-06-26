package com.kshrd.tnakrean.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ApiResponse<T> {

    public enum Status {

        OK, BAD_REQUEST,
        UNAUTHORIZED, EXCEPTION,
        VALIDATION_EXCEPTION,
        WRONG_CREDENTIAL,
        ACCESS_DENIED,
        NOT_FOUND,
        DUPLICATE_ENTRY, DELETED, CREATED, UPDATED
    }

    private int responseCode;
    private String responseMsg;
    private T data;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime time = LocalDateTime.now(ZoneOffset.of("+07:00"));


    // for bad request
    public static <T> ApiResponse<T> badRequest(String className) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(Status.BAD_REQUEST.toString());
        BaseMessage.obj = className;
        response.setResponseCode(400);
        return response;
    }

    public static <T> ApiResponse<T> ok(String className) {
        ApiResponse<T> response = new ApiResponse<>();
        BaseMessage.obj = className;
        response.setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage());
        response.setResponseCode(200);
        return response;
    }

    public static <T> ApiResponse<T> successDelete(String className) {
        ApiResponse<T> response = new ApiResponse<>();
        BaseMessage.obj = className;
        response.setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage());
        response.setResponseCode(200);
        return response;

    }
    // SUCCESS

    public static <T> ApiResponse<T> successCreate(String className) {
        ApiResponse<T> response = new ApiResponse<>();
        BaseMessage.obj = className;
        response.setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage());
        response.setResponseCode(201);
        return response;
    }

    //DuplicateEntry
    public static <T> ApiResponse<T> duplicateEntry(String className) {
        ApiResponse<T> response = new ApiResponse<>();
        BaseMessage.obj = className;
        response.setResponseMsg(Status.DUPLICATE_ENTRY.toString());
        response.setResponseCode(403);
        return response;

    }

    //UpdateSuccess
    public static <T> ApiResponse<T> updateSuccess(String className) {
        ApiResponse<T> response = new ApiResponse<>();
        BaseMessage.obj = className;
        response.setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage());
        response.setResponseCode(200);
        return response;
    }


    //notFound
    public static <T> ApiResponse<T> notFound(String className) {
        ApiResponse<T> response = new ApiResponse<>();
        BaseMessage.obj = className;
        response.setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
        response.setResponseCode(404);
        return response;

    }

    //Exception Error
    public static <T> ApiResponse<T> exception(Exception errorMsg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseCode(500);
        response.setResponseMsg(HttpStatus.FORBIDDEN.toString());
        return response;
    }


    public static <T> ApiResponse<T> setError(String errorMsg) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(errorMsg);
        return response;
    }

    // we need this class for the pagination purpose

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class PageMetaData {
        private final int size;
        private final long totalElement;
        private final int totalPages;
        private final int number;


        public PageMetaData(int size, long totalElement, int totalPages, int number) {

            this.size = size;
            this.totalElement = totalElement;
            this.totalPages = totalPages;
            this.number = number;
        }

    }

}
