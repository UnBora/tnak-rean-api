package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.repository.AppUserRepository;
import com.kshrd.tnakrean.service.serviceInter.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    final
    AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.loginByUserName(username);
    }
    @Override
    public void resetPassword(AppUserResponse appUserResponse) {
        appUserRepository.editPassword(appUserResponse);
    }


    @Override
    public void userRegister(UserRegisterRequest userRegisterRequest) {
        String encryptedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encryptedPassword);
        appUserRepository.userRegister(userRegisterRequest);
    }
}
