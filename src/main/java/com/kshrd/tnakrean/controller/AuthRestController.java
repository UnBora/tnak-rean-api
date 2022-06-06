package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.configuration.security.JwtTokenUtil;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.user.request.UserLoginRequest;
import com.kshrd.tnakrean.model.user.request.UserRegisterRequest;
import com.kshrd.tnakrean.model.user.request.UserUpdatePasswordRequestModel;
import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.model.user.response.UserRegisterResponse;
import com.kshrd.tnakrean.repository.AppUserRepository;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
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

    @PostMapping("/login")
    ResponseEntity<AppUserResponse> login(@RequestBody UserLoginRequest request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        JwtTokenUtil jwtUtils = new JwtTokenUtil();
        String token = jwtUtils.generateJwtToken(authentication);
        System.out.println("Here is the value of the token : " + token);

        UserDetails findUserByUsername = userServiceImp.loadUserByUsername(request.getUsername());
        AppUserResponse response = modelMapper.map(findUserByUsername, AppUserResponse.class);
        response.setToken(token);
        System.out.println(response.getRole());
        user_id = response.getId();
        return ResponseEntity.ok(response);

    }


    @PostMapping("/resetpassword/")
    public Object resetPassword(@RequestBody UserUpdatePasswordRequestModel userUpdatePasswordRequestModel) {
//        System.out.println("user_id"+user_id);
        System.out.println("old:" + userUpdatePasswordRequestModel.getOld_password() + "new" + userUpdatePasswordRequestModel.getNew_password());
        ApiResponse<String> response = new ApiResponse<>();
        String password = appUserRepository.getPassword(user_id);
//        System.out.println("getpass "+password);
        boolean is_match = passwordEncoder.matches(userUpdatePasswordRequestModel.getOld_password(), password);
        if (is_match) {
            String new_pass = passwordEncoder.encode(userUpdatePasswordRequestModel.getNew_password());
            userServiceImp.resetPassword(new_pass, user_id);
            return ApiResponse.updateSuccess();
        } else {
            return "Miss Match";
        }
    }


    @PostMapping("/register")
    ApiResponse<UserRegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            System.out.println(userRegisterRequest);
            userServiceImp.userRegister(userRegisterRequest);

            return ApiResponse.<UserRegisterResponse>successCreate()
                    .setData(modelMapper.map(userRegisterRequest, UserRegisterResponse.class));

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

}
