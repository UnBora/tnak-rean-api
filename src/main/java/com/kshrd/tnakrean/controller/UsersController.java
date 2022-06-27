package com.kshrd.tnakrean.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public ApiResponse<UserDeleteAccountRequest> deleteAccount(String password, String confirmPassword) {
        Integer userId = AuthRestController.user_id;
        String oldPassword = usersRepository.getPassword(userId);
        boolean isMatch = passwordEncoder.matches(password, oldPassword);
        try {
            if (!userId.equals(0)) {
                Integer status = usersRepository.getStatus(userId);
                if (password.equals(confirmPassword)) {
                    if (!status.equals(0)) {
                        if (isMatch) {
                            userServiceImp.userDeleteAccount(userId);
                            return ApiResponse.<UserDeleteAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                                    .setData(new UserDeleteAccountRequest(userId));
                        } else if (!isMatch) {
                            return ApiResponse.<UserDeleteAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg("Your password incorrect!")
                                    .setData(new UserDeleteAccountRequest(userId));
                        } else {
                            return ApiResponse.<UserDeleteAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                                    .setData(new UserDeleteAccountRequest(userId));
                        }
                    } else {
                        return ApiResponse.<UserDeleteAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                                .setResponseMsg("You Cannot delete the account that deleted!")
                                .setData(new UserDeleteAccountRequest(userId));
                    }
                } else {
                    return ApiResponse.<UserDeleteAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                            .setResponseMsg("You password and confirm password not matched!")
                            .setData(new UserDeleteAccountRequest(userId));
                }

            } else {
                return ApiResponse.<UserDeleteAccountRequest>unAuthorized(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/deactivate-account")
    public ApiResponse<UserDeactivateAccountRequest> deactivateAccount(String password, String confirmPassword) {
        Integer userId = AuthRestController.user_id;
        String oldPassword = usersRepository.getPassword(userId);
        boolean isMatch = passwordEncoder.matches(password, oldPassword);
        Integer status = usersRepository.getStatus(userId);
        try {
            if (!userId.equals(0)) {
                if (password.equals(confirmPassword)) {
                    if (status.equals(2)) {
                        if (isMatch) {
                            userServiceImp.userDeactivateAccount(userId);
                            return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                                    .setData(new UserDeactivateAccountRequest(userId));
                        } else if (!isMatch) {
                            return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg("Your password incorrect!")
                                    .setData(new UserDeactivateAccountRequest(userId));
                        } else {
                            return ApiResponse.<UserDeactivateAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage())
                                    .setData(new UserDeactivateAccountRequest(userId));
                        }
                    } else if (status.equals(1)) {
                        return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                                .setResponseMsg("You cannot deactivate the deactivate account!")
                                .setData(new UserDeactivateAccountRequest(userId));
                    } else {
                        return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                                .setResponseMsg("This account was Deleted!");
                    }
                } else {
                    return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                            .setResponseMsg("You password and confirm password not matched!")
                            .setData(new UserDeactivateAccountRequest(userId));
                }
            } else {
                return ApiResponse.<UserDeactivateAccountRequest>unAuthorized(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }

    }

    @PutMapping("/activate-account")
    public ApiResponse<UserActivateAccountRequest> activateAccount(String password, String passwordConfirm) {
        Integer userId = AuthRestController.user_id;
        String oldPassword = usersRepository.getPassword(userId);
        boolean isMatch = passwordEncoder.matches(password, oldPassword);
        Integer status = usersRepository.getStatus(userId);
        try {
            if (!userId.equals(0)) {
                if (password.equals(passwordConfirm)) {
                    if (status.equals(1)) {
                        if (isMatch) {
                            userServiceImp.userActivateAccount(userId);
                            return ApiResponse.<UserActivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                                    .setData(new UserActivateAccountRequest(userId));
                        } else if (!isMatch) {
                            return ApiResponse.<UserActivateAccountRequest>successDelete(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg("Your password incorrect!")
                                    .setData(new UserActivateAccountRequest(userId));
                        } else {
                            return ApiResponse.<UserActivateAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                                    .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage())
                                    .setData(new UserActivateAccountRequest(userId));
                        }
                    } else if (status.equals(2)) {
                        return ApiResponse.<UserActivateAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                                .setResponseMsg("You cannot activate the activate account!")
                                .setData(new UserActivateAccountRequest(userId));
                    } else {
                        return ApiResponse.<UserActivateAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                                .setResponseMsg("This account was Deleted!")
                                .setData(new UserActivateAccountRequest(userId));
                    }
                } else {
                    return ApiResponse.<UserActivateAccountRequest>setError(UserDeleteAccountRequest.class.getSimpleName())
                            .setResponseMsg("You password and confirm password not matched!")
                            .setData(new UserActivateAccountRequest(userId));
                }

            } else {
                return ApiResponse.<UserActivateAccountRequest>unAuthorized(UserDeleteAccountRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("update-profile")
    public ApiResponse<UserUpdateRequest> studentUpdateProfile(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        try {
            Integer userId = AuthRestController.user_id;
            if (!userId.equals(0)) {
                userServiceImp.updateProfileByID(userId, userUpdateRequest.getName(), userUpdateRequest.getUsername(), userUpdateRequest.getEmail(), userUpdateRequest.getGender());
                return ApiResponse.<UserUpdateRequest>ok(UserUpdateRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new UserUpdateRequest(userId, userUpdateRequest.getName(), userUpdateRequest.getUsername(), userUpdateRequest.getEmail(), userUpdateRequest.getImg(), userUpdateRequest.getGender()));
            } else {
                return ApiResponse.<UserUpdateRequest>unAuthorized(UserUpdateRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
