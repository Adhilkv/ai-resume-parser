package com.ctrlaltthink.ai_resume_parser.service.auth;

import com.ctrlaltthink.ai_resume_parser.dto.auth.AuthenticationRequest;
import com.ctrlaltthink.ai_resume_parser.dto.auth.AuthenticationResponse;
import com.ctrlaltthink.ai_resume_parser.dto.auth.RefreshRequest;
import com.ctrlaltthink.ai_resume_parser.dto.auth.RegistrationRequest;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);
}
