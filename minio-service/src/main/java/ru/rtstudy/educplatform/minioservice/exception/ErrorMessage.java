package ru.rtstudy.educplatform.minioservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;
    private String description;
    private LocalDateTime currentTime;
    private String endpoint;
}
