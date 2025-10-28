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
public class ProfileUpdateRequest {

    @NotBlank(message = "VALIDATION.REGISTRATION.FIRST_NAME.BLANK")
    @Size(min = 1, max = 50, message = "VALIDATION.REGISTRATION.FIRST_NAME.SIZE")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "VALIDATION.REGISTRATION.FIRST_NAME.PATTERN")
    @Schema(example = "Adk")
    private String firstName;

    @NotBlank(message = "VALIDATION.REGISTRATION.LAST_NAME.BLANK")
    @Size(min = 1, max = 50, message = "VALIDATION.REGISTRATION.LAST_NAME.SIZE")
    @Pattern(regexp = "^[\\p{L} '-]+$", message = "VALIDATION.REGISTRATION.LAST_NAME.PATTERN")
    @Schema(example = "Kv")
    private String lastName;
}
