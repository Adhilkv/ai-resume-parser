package com.ctrlaltthink.ai_resume_parser.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void updateProfileInfo();

    void changePassword();

    void deactivateAccount();

    void registerAccount();

    void deleteAccount();
}
