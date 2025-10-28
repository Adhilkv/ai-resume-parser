package com.ctrlaltthink.ai_resume_parser.service.auth.impl;

import com.ctrlaltthink.ai_resume_parser.dto.ErrorCode;
import com.ctrlaltthink.ai_resume_parser.dto.auth.AuthenticationRequest;
import com.ctrlaltthink.ai_resume_parser.dto.auth.AuthenticationResponse;
import com.ctrlaltthink.ai_resume_parser.dto.auth.RefreshRequest;
import com.ctrlaltthink.ai_resume_parser.dto.auth.RegistrationRequest;
import com.ctrlaltthink.ai_resume_parser.entity.Role;
import com.ctrlaltthink.ai_resume_parser.entity.User;
import com.ctrlaltthink.ai_resume_parser.exception.BusinessException;
import com.ctrlaltthink.ai_resume_parser.repository.RoleRepository;
import com.ctrlaltthink.ai_resume_parser.repository.UserRepository;
import com.ctrlaltthink.ai_resume_parser.service.auth.AuthenticationService;
import com.ctrlaltthink.ai_resume_parser.service.auth.JwtService;
import com.ctrlaltthink.ai_resume_parser.service.user.impl.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        final User user = (User) auth.getPrincipal();
        final String token = this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        final Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exist"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user = this.userMapper.toUser(request);
        user.setRoles(roles);

        log.debug("Saving new user: {}", user.getEmail());

        this.userRepository.save(user);

        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);

        this.roleRepository.save(userRole);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken = this.jwtService.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();
    }





    private void checkUserEmail(String email) {
        final boolean exists = this.userRepository.existsByEmailIgnoreCase(email);
        if (exists) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkPasswords(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }


}
