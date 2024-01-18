package ru.rtstudy.educplatformsecurity.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.rtstudy.educplatformsecurity.model.User;

@Component
public class Util {

    public User findUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
