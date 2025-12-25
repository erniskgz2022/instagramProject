package java19.instagramproject.config.security;

import java19.instagramproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {
    public User getCurrentUser() throws AccessDeniedException {
        // SecurityContext'тен Authentication объектин алабыз
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Эгер authentication жок же логин болбогон болсо
        // Бул учурда 401 ЭМЕС, 403 кайтарышыбыз керек
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("User not authorized.");
        }
        // Authentication'деги негизги объект (principal)
        Object principal = auth.getPrincipal();
        // Эгер principal биздин User entity болсо  кайтарабыз
        if (principal instanceof User user) {
            return user;
        }
        // Эгер principal башка тип болсо (anonymousUser ж.б.)
        throw new AccessDeniedException("Invalid authentication principal");
    }

}
