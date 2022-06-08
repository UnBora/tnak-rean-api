package com.kshrd.tnakrean.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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
    private Object metadata;


    // for bad request
    // for bad request
    public static <T> ApiResponse<T> badRequest() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(Status.BAD_REQUEST.toString());
        response.setResponseCode(400);
        return response;
    }

    public static <T> ApiResponse<T> ok() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(Status.OK.toString());
        response.setResponseCode(200);
        return response;
    }

    public static <T> ApiResponse<T> successDelete() {

        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(Status.DELETED.toString());
        response.setResponseCode(200);
        return response;

    }
    // SUCCESS

    public static <T> ApiResponse<T> successCreate() {

        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(Status.CREATED.toString());
        response.setResponseCode(201);
        return response;
    }

    //DuplicateEntry
    public static <T> ApiResponse<T> duplicateEntry() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(Status.DUPLICATE_ENTRY.toString());
        response.setResponseCode(403);
        return response;

    }

    //UpdateSuccess
    public static <T> ApiResponse<T> updateSuccess() {

        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(Status.UPDATED.toString());
        response.setResponseCode(200);
        return response;
    }


    //notFound
    public static <T> ApiResponse<T> notFound() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponseMsg(Status.NOT_FOUND.toString());
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
