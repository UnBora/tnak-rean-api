package com.kshrd.tnakrean.model.apiresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.format.DateTimeFormatter;
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
        DUPLICATE_ENTRY, SUCCESS_DELETE, CREATE_SUCCESS, UPDATE_SUCCESS
    }

    private T payload;
    private Object error;
    private boolean success = false;
    private Object metadata;
    private Status status;


    // for bad request
    // for bad request
    public static <T> ApiResponse<T> badRequest() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(Status.BAD_REQUEST);
        return response;
    }

    public static <T> ApiResponse<T> ok() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(Status.OK);
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> successDelete() {

        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(Status.SUCCESS_DELETE);
        response.setSuccess(true);
        return response;

    }
    // SUCCESS

    public static <T> ApiResponse<T> successCreate() {

        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(Status.CREATE_SUCCESS);
        return response;
    }

    //DuplicateEntry
    public static <T> ApiResponse<T> duplicateEntry() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(Status.DUPLICATE_ENTRY);
        return response;

    }


    //notFound
    public static <T> ApiResponse<T> notFound() {

        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(Status.NOT_FOUND);
        return response;

    }


    //Exception Error
    public static <T> ApiResponse<T> exception() {

        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(Status.EXCEPTION);
        return response;

    }

    public static <T> ApiResponse<T> updateSuccess() {

        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(Status.UPDATE_SUCCESS);
        return response;
    }

    public void setErrorMsgToResponse(String errorMsg, Exception ex) {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        ResponseError error = new ResponseError()

                .setDetails(errorMsg)
                .setMessage(ex.getMessage())
                .setTimestamp(new Date());


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
