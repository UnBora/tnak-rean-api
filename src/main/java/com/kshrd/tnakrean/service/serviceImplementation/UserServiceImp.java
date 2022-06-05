package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.repository.AppUserRepository;
import com.kshrd.tnakrean.service.serviceInter.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {
    final
    AppUserRepository appUserRepository;

    public UserServiceImp(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.loginByUserName(username);
    }
    @Override
    public void resetPassword(String new_password,int user_id) {
        appUserRepository.editPassword(new_password,user_id);
    }
}
