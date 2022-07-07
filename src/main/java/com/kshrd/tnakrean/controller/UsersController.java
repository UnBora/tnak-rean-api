package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.request.UserDeactivateAccountRequest;
import com.kshrd.tnakrean.model.user.request.UserDeleteAccountRequest;
import com.kshrd.tnakrean.model.user.request.UserUpdateRequest;
import com.kshrd.tnakrean.model.user.request.UserActivateAccountRequest;
import com.kshrd.tnakrean.model.user.response.GetNotificationResponse;
import com.kshrd.tnakrean.repository.UsersRepository;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
@Slf4j
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
    public ApiResponse<UserDeleteAccountRequest> deleteAccount(
            @RequestParam @Size(min = 3, max = 16, message = "{validation.password.sizenotlesthen3}") String password,
            @RequestParam @Size(min = 3, max = 16, message = "{validation.password.sizenotlesthen3}")String confirmPassword) {
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
                                    .setResponseMsg("Your account delete successfully")
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
    public ApiResponse<UserDeactivateAccountRequest> deactivateAccount(
            @RequestParam @Size(min = 3, max = 16,message = "{validation.password.sizenotlesthen3}") String password,
            @RequestParam @Size(min = 3, max = 16, message = "{validation.password.sizenotlesthen3}") String confirmPassword) {
        System.out.println("user id:"+AuthRestController.user_id);
        Integer userId = AuthRestController.user_id;
        String oldPassword = usersRepository.getPassword(userId);
        Integer status = usersRepository.getStatus(userId);

        try {
            boolean isMatch = passwordEncoder.matches(password, oldPassword);
            if (!userId.equals(0)) {
                if (password.equals(confirmPassword)) {
                    if (status.equals(2)) {
                        if (isMatch) {
                            userServiceImp.userDeactivateAccount(userId);
                            return ApiResponse.<UserDeactivateAccountRequest>updateSuccess(UserDeactivateAccountRequest.class.getSimpleName())
                                    .setResponseMsg("Your account deactivated successfully")
                                    .setData(new UserDeactivateAccountRequest(userId));
                        } else if (!isMatch) {
                            return ApiResponse.<UserDeactivateAccountRequest>notFound(UserDeactivateAccountRequest.class.getSimpleName())
                                    .setResponseMsg("Your password incorrect!")
                                    .setData(new UserDeactivateAccountRequest(userId));
                        } else {
                            return ApiResponse.<UserDeactivateAccountRequest>setError(UserDeactivateAccountRequest.class.getSimpleName())
                                    .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage())
                                    .setData(new UserDeactivateAccountRequest(userId));
                        }
                    } else if (status.equals(1)) {
                        return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeactivateAccountRequest.class.getSimpleName())
                                .setResponseMsg("You cannot deactivate the deactivate account!")
                                .setData(new UserDeactivateAccountRequest(userId));
                    } else {
                        return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeactivateAccountRequest.class.getSimpleName())
                                .setResponseMsg("This account was Deleted!");
                    }
                } else {
                    return ApiResponse.<UserDeactivateAccountRequest>successDelete(UserDeactivateAccountRequest.class.getSimpleName())
                            .setResponseMsg("You password and confirm password not matched!")
                            .setData(new UserDeactivateAccountRequest(userId));
                }
            } else {
                return ApiResponse.<UserDeactivateAccountRequest>unAuthorized(UserDeactivateAccountRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }

    }

    @PutMapping("/activate-account")
    public ApiResponse<UserActivateAccountRequest> activateAccount(
            @RequestParam @Size(min = 3, max = 16, message = "{validation.password.sizenotlesthen3}") String password,
            @RequestParam @Size(min = 3, max = 16, message = "{validation.password.sizenotlesthen3}")String passwordConfirm) {
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
                            String simpleName = UserActivateAccountRequest.class.getSimpleName();
                            System.out.println("simple name:"+simpleName);
                            String message = BaseMessage.Success.UPDATE_SUCCESS.getMessage();

                            return ApiResponse.<UserActivateAccountRequest>updateSuccess(UserActivateAccountRequest.class.getSimpleName())
                                    .setResponseMsg("A Record of UserActivateAccountRequest has  been updated successfully")
                                    .setData(new UserActivateAccountRequest(userId));
                        } else if (!isMatch) {
                            return ApiResponse.<UserActivateAccountRequest>successDelete(UserActivateAccountRequest.class.getSimpleName())
                                    .setResponseMsg("Your password incorrect!")
                                    .setData(new UserActivateAccountRequest(userId));
                        } else {
                            return ApiResponse.<UserActivateAccountRequest>setError(UserActivateAccountRequest.class.getSimpleName())
                                    .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage())
                                    .setData(new UserActivateAccountRequest(userId));
                        }
                    } else if (status.equals(2)) {
                        return ApiResponse.<UserActivateAccountRequest>setError(UserActivateAccountRequest.class.getSimpleName())
                                .setResponseMsg("You cannot activate the activate account!")
                                .setData(new UserActivateAccountRequest(userId));
                    } else {
                        return ApiResponse.<UserActivateAccountRequest>setError(UserActivateAccountRequest.class.getSimpleName())
                                .setResponseMsg("This account was Deleted!")
                                .setData(new UserActivateAccountRequest(userId));
                    }
                } else {
                    return ApiResponse.<UserActivateAccountRequest>setError(UserActivateAccountRequest.class.getSimpleName())
                            .setResponseMsg("You password and confirm password not matched!")
                            .setData(new UserActivateAccountRequest(userId));
                }
            } else {
                return ApiResponse.<UserActivateAccountRequest>unAuthorized(UserActivateAccountRequest.class.getSimpleName())
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
                Boolean checkUsername= usersRepository.checkUserName(userUpdateRequest.getUsername());
                Boolean checkEmail= usersRepository.checkEmailExist(userUpdateRequest.getEmail());
                String email= usersRepository.selectEmail(userId);
                String name=usersRepository.catchName(userUpdateRequest.getUsername().trim());
                if (checkUsername.equals(true)&&(!(name.equals(userUpdateRequest.getUsername().trim())))){
                    return ApiResponse.<UserUpdateRequest>badRequest(UserUpdateRequest.class.getSimpleName())
                            .setResponseMsg("This Username is exist!");
                }else if (checkEmail.equals(true)&&(!(email.equals(userUpdateRequest.getEmail().trim())))){
                    return ApiResponse.<UserUpdateRequest>badRequest(UserUpdateRequest.class.getSimpleName())
                            .setResponseMsg("This Email is Exist!");
                }else {
                    userServiceImp.updateProfileByID(userId, userUpdateRequest.getName(), userUpdateRequest.getUsername(), userUpdateRequest.getEmail(), userUpdateRequest.getGender());
                    return ApiResponse.<UserUpdateRequest>ok(UserUpdateRequest.class.getSimpleName())
                            .setResponseMsg("Your account activated successfully")
                            .setData(new UserUpdateRequest(userId, userUpdateRequest.getName(), userUpdateRequest.getUsername(), userUpdateRequest.getEmail(), userUpdateRequest.getImg(), userUpdateRequest.getGender()));
                }
            } else {
                return ApiResponse.<UserUpdateRequest>unAuthorized(UserUpdateRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("get-notification-by-userId")
    public ApiResponse<List<GetNotificationResponse>> getNotificationByUserId(){
            Integer userId= AuthRestController.user_id;
        try {
            if (userId.equals(0)){
                return ApiResponse.<List<GetNotificationResponse>>notFound(GetNotificationResponse.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");

            }else {
                List<GetNotificationResponse> getNotificationResponse=usersRepository.getNotificationByUserId(userId);
                return ApiResponse.<List<GetNotificationResponse>>ok(GetNotificationResponse.class.getSimpleName())
                        .setResponseMsg("Get notification successfully")
                        .setData(getNotificationResponse);
            }
        }catch (Exception e){
            return ApiResponse.setError(e.getMessage());
        }

    }

}
