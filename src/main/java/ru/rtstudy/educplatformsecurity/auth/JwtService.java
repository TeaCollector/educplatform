package ru.rtstudy.educplatformsecurity.auth;

import org.springframework.security.core.userdetails.UserDetails;
import ru.rtstudy.educplatformsecurity.model.User;

public interface JwtService {

    String extractUser(String token);

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(User user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
