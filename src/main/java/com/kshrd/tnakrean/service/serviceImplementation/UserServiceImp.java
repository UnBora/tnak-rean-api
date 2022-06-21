package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.user.request.UserRegisterRequest;
import com.kshrd.tnakrean.repository.AppUserRepository;
import com.kshrd.tnakrean.repository.UsersRepository;
import com.kshrd.tnakrean.service.serviceInter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService {
    final
    AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    final UsersRepository usersRepository;

    @Autowired
    public UserServiceImp(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.loginByUserName(username);
    }

    @Override
    public void resetPassword(String new_password, int user_id) {
        appUserRepository.editPassword(new_password, user_id);
    }

    @Override
    public void userDeleteAccount(Integer id) {
        usersRepository.deleteAccount(id);
    }

    @Override
    public void usertDeactivateAccount(Integer id) {
        usersRepository.deactivateAccount(id);
    }

    @Override
    public void userActivateAccount(Integer id) {
        usersRepository.activateAccount(id);
    }


    @Override
    public void userRegister(UserRegisterRequest userRegisterRequest) {
        String encryptedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encryptedPassword);
        appUserRepository.userRegister(userRegisterRequest);
    }


}
