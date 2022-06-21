package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.request.StudentRequest;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController {

    final UserServiceImp userServiceImp;
    @Autowired
    public UsersController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @DeleteMapping("/delete-account")
    public ApiResponse<StudentRequest> deleteAccount() {
        Integer user_id = AuthRestController.user_id;

        userServiceImp.userDeleteAccount(user_id);
        return ApiResponse.<StudentRequest>successDelete("student class")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(new StudentRequest(user_id));

    }

    @PutMapping("/deactivate-account")
    public ApiResponse<StudentRequest> deactivateAccount() {
        Integer user_id = AuthRestController.user_id;

        userServiceImp.usertDeactivateAccount(user_id);
        return ApiResponse.<StudentRequest>updateSuccess(StudentRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new StudentRequest(user_id));

    }

    @PutMapping("/activate-account")
    public ApiResponse<StudentRequest> activateAccount() {
        Integer user_id = AuthRestController.user_id;

        userServiceImp.userActivateAccount(user_id);
        return ApiResponse.<StudentRequest>updateSuccess(StudentRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new StudentRequest(user_id));
    }
}
