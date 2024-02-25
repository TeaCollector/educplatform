package ru.rtstudy.educplatformsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import ru.rtstudy.educplatformsecurity.exception.ErrorMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class EducAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.warn("User: " + auth.getName()
                     + " attempted to access the protected URL: "
                     + request.getRequestURI());
        }
        ErrorMessage error = ErrorMessage.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .description(authException.getMessage())
                .currentTime(LocalDateTime.now())
                .endpoint(request.getRequestURI())
                .build();
        response.setStatus(401);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writeValue(response.getOutputStream(), error);
    }
}
