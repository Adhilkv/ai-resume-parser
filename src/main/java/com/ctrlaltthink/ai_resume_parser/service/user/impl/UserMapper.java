package com.ctrlaltthink.ai_resume_parser.service.user.impl;

import com.ctrlaltthink.ai_resume_parser.dto.auth.RegistrationRequest;
import com.ctrlaltthink.ai_resume_parser.dto.user.ProfileUpdateRequest;
import com.ctrlaltthink.ai_resume_parser.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public void mergeUserInfo(User user, ProfileUpdateRequest request) {

        if(StringUtils.isNoneBlank(request.getFirstName())
                && !user.getFirstName().equals(request.getFirstName())){
            user.setFirstName(request.getFirstName());
        }

        if(StringUtils.isNoneBlank(request.getLastName())
                && !user.getLastName().equals(request.getLastName())){
            user.setLastName(request.getLastName());
        }
    }

    public User toUser(RegistrationRequest request) {
            return User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(this.passwordEncoder.encode(request.getPassword()))
                    .enabled(true)
                    .locked(false)
                    .credentialsExpired(false)
                    .build();
    }
}
