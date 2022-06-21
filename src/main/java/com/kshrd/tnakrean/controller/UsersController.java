package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.request.UserDeactivateAccountRequest;
import com.kshrd.tnakrean.model.user.request.UserDeleteAccountRequest;
import com.kshrd.tnakrean.model.user.request.UserUpdateRequest;
import com.kshrd.tnakrean.model.user.request.UserActivateAccountRequest;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController {

    final UserServiceImp userServiceImp;
    @Autowired
    public UsersController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @DeleteMapping("/delete-account")
    public ApiResponse<UserDeleteAccountRequest> deleteAccount() {
        Integer user_id = AuthRestController.user_id;

        userServiceImp.userDeleteAccount(user_id);
        return ApiResponse.<UserDeleteAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(new UserDeleteAccountRequest(user_id));

    }

    @PutMapping("/deactivate-account")
    public ApiResponse<UserDeactivateAccountRequest> deactivateAccount() {
        Integer user_id = AuthRestController.user_id;

        userServiceImp.usertDeactivateAccount(user_id);
        return ApiResponse.<UserDeactivateAccountRequest>updateSuccess(UserDeactivateAccountRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new UserDeactivateAccountRequest(user_id));

    }

    @PutMapping("/activate-account")
    public ApiResponse<UserActivateAccountRequest> activateAccount() {
        Integer user_id = AuthRestController.user_id;

        userServiceImp.userActivateAccount(user_id);
        return ApiResponse.<UserActivateAccountRequest>updateSuccess(UserActivateAccountRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new UserActivateAccountRequest(user_id));
    }

    @PutMapping("update-profile")
    public ApiResponse<UserUpdateRequest> studentUpdateProfile(@RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            Integer user_id = AuthRestController.user_id;
            userServiceImp.updateprofileByID(user_id, userUpdateRequest.getName(), userUpdateRequest.getUsername(), userUpdateRequest.getGender());
            return ApiResponse.<UserUpdateRequest>ok(UserUpdateRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(new UserUpdateRequest(user_id, userUpdateRequest.getName(), userUpdateRequest.getUsername(), userUpdateRequest.getGender()));

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
