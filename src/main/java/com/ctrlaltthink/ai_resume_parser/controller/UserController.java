package com.ctrlaltthink.ai_resume_parser.controller;


import com.ctrlaltthink.ai_resume_parser.dto.user.PasswordChangeRequest;
import com.ctrlaltthink.ai_resume_parser.dto.user.ProfileUpdateRequest;
import com.ctrlaltthink.ai_resume_parser.entity.User;
import com.ctrlaltthink.ai_resume_parser.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name= "User", description = "User APIs")
public class UserController {

    private UserService service;

    public void updateProfileInfo(@RequestBody @Valid final ProfileUpdateRequest request, final Authentication principal) {
        this.service.updateProfileInfo(request, getUserId(principal));
    }

    @PostMapping("/me/password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestBody
            @Valid
            final PasswordChangeRequest request,
            final Authentication principal) {
        this.service.changePassword(request, getUserId(principal));
    }

    @PatchMapping("/me/deactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deactivateAccount(final Authentication principal) {
        this.service.deactivateAccount(getUserId(principal));
    }

    @PatchMapping("/me/reactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void reactivateAccount(final Authentication principal) {
        this.service.activateAccount(getUserId(principal));
    }

    @DeleteMapping("/me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAccount(final Authentication principal) {
        this.service.deleteAccount(getUserId(principal));
    }

    private String getUserId(final Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }
}
