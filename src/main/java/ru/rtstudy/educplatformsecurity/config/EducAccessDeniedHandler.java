package ru.rtstudy.educplatformsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.rtstudy.educplatformsecurity.exception.ErrorMessage;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
//@JsonSerialize(using = LocalDateTimeSerializer.class)
public class EducAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.warn("User: " + auth.getName()
                     + " attempted to access the protected URL: "
                     + request.getRequestURI());
        }
        ErrorMessage error = ErrorMessage.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .description(new AccessDeniedException("Access denied").getMessage())
                .currentTime(LocalDateTime.now())
                .endpoint(request.getRequestURI())
                .build();
        response.setStatus(403);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writeValue(response.getOutputStream(), error);
    }
}