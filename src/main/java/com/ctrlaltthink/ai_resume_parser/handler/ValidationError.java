package com.ctrlaltthink.ai_resume_parser.handler;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ValidationError {

    private String field;
    private String message;
    private String code;
}
