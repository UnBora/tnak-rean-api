package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.configuration.security.JwtTokenUtil;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.request.UserLoginRequest;
import com.kshrd.tnakrean.model.user.request.UserRegisterRequest;
import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.model.user.request.UserUpdatePasswordRequestModel;
import com.kshrd.tnakrean.model.user.response.UserRegisterResponse;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import com.kshrd.tnakrean.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    AppUserRepository appUserRepository;

    ModelMapper modelMapper = new ModelMapper();


    PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    static int user_id;
    static AppUserResponse userDetails;

    @PostMapping("/login")
    ResponseEntity<AppUserResponse> login(@RequestBody UserLoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        JwtTokenUtil jwtUtils = new JwtTokenUtil();
        String token = jwtUtils.generateJwtToken(authentication);
//        System.out.println("Here is the value of the token : " + token);
        Boolean checkName= appUserRepository.checkUserName(request.getUsername());
        UserDetails findUserByUsername = userServiceImp.loadUserByUsername(request.getUsername());
        userDetails  = modelMapper.map(findUserByUsername, AppUserResponse.class);
        userDetails.setToken(token);
        System.out.println(userDetails.getRole());
        user_id = userDetails.getId();
        return ResponseEntity.ok(userDetails);

    }


    @PostMapping("/update-password")
    public ApiResponse<Boolean> updatePassword(@RequestBody UserUpdatePasswordRequestModel userUpdatePasswordRequestModel) {

        ApiResponse<String> response = new ApiResponse<>();
        String password = appUserRepository.getPassword(user_id);
        boolean is_match = passwordEncoder.matches(userUpdatePasswordRequestModel.getOld_password(), password);
        try {
            if (is_match) {
                String new_pass = passwordEncoder.encode(userUpdatePasswordRequestModel.getNew_password());
                userServiceImp.resetPassword(new_pass, user_id);
                return ApiResponse.<Boolean>updateSuccess("User")
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage());
            } else {
                return response.setError("Your old password did not matched!");
            }
        } catch (Exception e) {
            return response.setError(e.getMessage());
        }

    }

    @PostMapping("/register")
    ApiResponse<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        try {
            Boolean checkEmail = appUserRepository.checkEmailExist(userRegisterRequest.getEmail());
            Boolean checkUserName= appUserRepository.checkUserName(userRegisterRequest.getUsername());
            Integer checkUserRole = userRegisterRequest.getUser_role_id();
            if (checkEmail.equals(true)) {
                return ApiResponse.<UserRegisterResponse>badRequest(UserRegisterResponse.class.getSimpleName())
                        .setResponseMsg("This Email has been exist!");
            }else if(!(checkUserRole.equals(1)||(checkUserRole.equals(2)))){
                return ApiResponse.<UserRegisterResponse>badRequest(UserRegisterResponse.class.getSimpleName())
                        .setResponseMsg("User role can use only number 1(Student) or 2(Teacher)!");
            }else if(checkUserName.equals(true)){
                return ApiResponse.<UserRegisterResponse>badRequest(UserRegisterResponse.class.getSimpleName())
                        .setResponseMsg("This Username has been exist!");
            } else {
                userServiceImp.userRegister(userRegisterRequest);
                Integer userId= appUserRepository.lastUserId();
                appUserRepository.studenRegistrationAndRequese(userId, userRegisterRequest.getClassroomId(),userRegisterRequest.getClassId());
                return ApiResponse.<UserRegisterResponse>successCreate(UserRegisterResponse.class.getSimpleName())
                        .setData(modelMapper.map(userRegisterRequest, UserRegisterResponse.class));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

}
