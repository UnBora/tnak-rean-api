package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.request.UserDeactivateAccountRequest;
import com.kshrd.tnakrean.model.user.request.UserDeleteAccountRequest;
import com.kshrd.tnakrean.model.user.request.UserUpdateRequest;
import com.kshrd.tnakrean.model.user.request.UserActivateAccountRequest;
import com.kshrd.tnakrean.repository.UsersRepository;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UsersController {

    final UserServiceImp userServiceImp;
    final UsersRepository usersRepository;

    @Autowired
    public UsersController(UserServiceImp userServiceImp, UsersRepository usersRepository) {
        this.userServiceImp = userServiceImp;
        this.usersRepository = usersRepository;
    }

    PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @DeleteMapping("/delete-account")
    public ApiResponse<UserDeleteAccountRequest> deleteAccount(String password) {
        Integer user_id = AuthRestController.user_id;
        String oldPassword =usersRepository.getPassword(user_id);
        boolean isMatch = passwordEncoder.matches(password, oldPassword);
        try {
            if(isMatch){
                userServiceImp.userDeleteAccount(user_id);
                return ApiResponse.<UserDeleteAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                        .setData(new UserDeleteAccountRequest(user_id));
            }else if (isMatch==false){
                return ApiResponse.<UserDeleteAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg("Your password Invalid!")
                        .setData(new UserDeleteAccountRequest(user_id));
            }else {
                return ApiResponse.<UserDeleteAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                        .setData(new UserDeleteAccountRequest(user_id));
            }
        }catch (Exception e){
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/deactivate-account")
    public ApiResponse<UserDeactivateAccountRequest> deactivateAccount(String password) {
        Integer user_id = AuthRestController.user_id;
        String oldPassword =usersRepository.getPassword(user_id);
        boolean isMatch = passwordEncoder.matches(password, oldPassword);
        try {
            if(isMatch){
                userServiceImp.userDeactivateAccount(user_id);
                return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new UserDeactivateAccountRequest(user_id));
            }else if (isMatch==false){
                return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg("Your password Invalid!")
                        .setData(new UserDeactivateAccountRequest(user_id));
            }else {
                return ApiResponse.<UserDeactivateAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage())
                        .setData(new UserDeactivateAccountRequest(user_id));
            }
        }catch (Exception e){
            return ApiResponse.setError(e.getMessage());
        }

    }

    @PutMapping("/activate-account")
    public ApiResponse<UserActivateAccountRequest> activateAccount(String password) {
        Integer user_id = AuthRestController.user_id;
        String oldPassword =usersRepository.getPassword(user_id);
        boolean isMatch = passwordEncoder.matches(password, oldPassword);
        try {
            if(isMatch){
                userServiceImp.userActivateAccount(user_id);
                return ApiResponse.<UserActivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new UserActivateAccountRequest(user_id));
            }else if (isMatch==false){
                return ApiResponse.<UserActivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg("Your password Invalid!")
                        .setData(new UserActivateAccountRequest(user_id));
            }else {
                return ApiResponse.<UserActivateAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage())
                        .setData(new UserActivateAccountRequest(user_id));
            }
        }catch (Exception e){
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("update-profile")
    public ApiResponse<UserUpdateRequest> studentUpdateProfile(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        try {
            Integer user_id = AuthRestController.user_id;
            userServiceImp.updateProfileByID(user_id, userUpdateRequest.getName(), userUpdateRequest.getUsername(), userUpdateRequest.getGender());
            return ApiResponse.<UserUpdateRequest>ok(UserUpdateRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(new UserUpdateRequest(user_id, userUpdateRequest.getName(), userUpdateRequest.getUsername(), userUpdateRequest.getGender()));

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
