package com.ctrlaltthink.ai_resume_parser.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordChangeRequest {

    @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.BLANK")
    private String currentPassword;

    @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.BLANK")
    @Size(min = 8, max = 72, message = "VALIDATION.REGISTRATION.PASSWORD.SIZE")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$", message = "VALIDATION.REGISTRATION.PASSWORD.WEAK")
    @Schema(example = "pAssword1!_")
    private String newPassword;

    @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.BLANK")
    @Size(min = 8, max = 72, message = "VALIDATION.REGISTRATION.PASSWORD.SIZE")
    @Schema(example = "pAssword1!_")
    private String confirmNewPassword;
}
