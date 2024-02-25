package ru.rtstudy.educplatformsecurity.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateErrorMessage {
    private String code;
    private String detail;
    private String source;

}
