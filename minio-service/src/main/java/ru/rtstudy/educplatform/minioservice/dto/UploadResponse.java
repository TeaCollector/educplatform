package ru.rtstudy.educplatform.minioservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadResponse {
    String objectName;
}
