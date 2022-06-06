package com.kshrd.tnakrean.service.serviceInter;


import com.kshrd.tnakrean.model.user.request.UserRegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    void resetPassword(String new_password,int user_id);

    void userRegister(UserRegisterRequest userRegisterRequest);

}
