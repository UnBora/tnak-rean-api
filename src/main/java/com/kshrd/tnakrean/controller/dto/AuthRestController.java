package com.kshrd.tnakrean.controller.dto;

import com.kshrd.tnakrean.configuration.security.JwtTokenUtil;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.user.request.UserLoginRequest;
import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.service.serviceImplementation.UserServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImp userServiceImp;

    ModelMapper modelMapper = new ModelMapper();

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
        return ResponseEntity.ok(response);

    }

    @PostMapping("/edit")
    public ResponseEntity<String> createUser(@RequestBody AppUserResponse appUserResponse){
        userServiceImp.resetPassword(appUserResponse);
        return ResponseEntity.ok().body("testing success");
    }


}
