package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.user.request.UserRegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    void userRegister(UserRegisterRequest userRegisterRequest);
    void resetPassword(String new_password,int user_id);

    //    Delete Student User
    void userDeleteAccount(int user_id);

    //    Student Deactivate account
    void userDeactivateAccount( int user_id);

    //    Student activate x
    void userActivateAccount(int user_id);

    void updateProfileByID(Integer user_id, String name, String username, String email, String gender);

//    User get notification by ID
    void getnotificationById(Integer userId);

}
