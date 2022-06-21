package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.user.request.UserRegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    void userRegister(UserRegisterRequest userRegisterRequest);
    void resetPassword(String new_password,int user_id);


    //    Delete Student User
    void userDeleteAccount(Integer id);

    //    Student Deactivate account
    void usertDeactivateAccount(Integer id);

    //    Student activate x
    void userActivateAccount(Integer id);


    void updateprofileByID(Integer user_id, String name, String username, String gender);

}
