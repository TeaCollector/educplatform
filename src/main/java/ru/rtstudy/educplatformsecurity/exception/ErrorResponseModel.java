package ru.rtstudy.educplatformsecurity.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseModel {

    public String type;
    public List<ValidateErrorMessage> errorModels;
}
