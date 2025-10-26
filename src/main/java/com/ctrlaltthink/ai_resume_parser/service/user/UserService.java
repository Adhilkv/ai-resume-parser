package com.ctrlaltthink.ai_resume_parser.service.user;

import com.ctrlaltthink.ai_resume_parser.dto.user.PasswordChangeRequest;
import com.ctrlaltthink.ai_resume_parser.dto.user.ProfileUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void updateProfileInfo(ProfileUpdateRequest request, String userId);

    void changePassword(PasswordChangeRequest request, String userId);

    void deactivateAccount(String userId);

    void activateAccount(String userId);

    void deleteAccount(String userId);
}
