package com.ctrlaltthink.ai_resume_parser.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeRequest {

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
