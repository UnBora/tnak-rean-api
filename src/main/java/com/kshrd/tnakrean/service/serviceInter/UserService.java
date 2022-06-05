package com.kshrd.tnakrean.service.serviceInter;

import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    void resetPassword(String new_password,int user_id);


}
