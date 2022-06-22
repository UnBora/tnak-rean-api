package com.kshrd.tnakrean.model.apiresponse;

import org.springframework.stereotype.Component;

@Component
public class BaseMessage {
    public static String obj;
    public enum Success{
        INSERT_SUCCESS("A Record of " +obj+" has been inserted successfully"),
        UPDATE_SUCCESS("A Record of " +obj+" has  been updated successfully"),
        SELECT_ALL_RECORD_SUCCESS("All Records of " +obj+" have been found"),
        SELECT_ONE_RECORD_SUCCESS("A Record of " +obj+" has been found"),
        DELETE_SUCCESS("A Record of " +obj+" has has been deleted successfully");

        private String message;

        Success(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

    public enum Error{
        //ERROR("Path variable is wrong in this request. Please change it to your request."),
        SELECT_ERROR("The Record of " +obj+" has cannot be found"),
        INSERT_ERROR("Inserting of " +obj+" has  been failed"),
        UPDATE_ERROR("Updating of " +obj+" has has been failed"),
        DELETE_ERROR("Deleting of " +obj+" has  been failed");

        private  String message;

        Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
