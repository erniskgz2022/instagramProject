package java19.instagramproject.config.security;

import java19.instagramproject.entity.User;
import java19.instagramproject.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.security.access.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class AccessGuard {
    private final CurrentUserProvider currentUserProvider;

    public void allow(Long ownerId) throws AccessDeniedException {
        // Токендеги user
        User u = currentUserProvider.getCurrentUser();
        // ADMIN эмес жана owner эмес болсо  тыюу салынат
        if (u.getRole() != Role.ADMIN && !u.getId().equals(ownerId)) {
            throw new AccessDeniedException("Access denied");
        }
    }

    public void admin() throws AccessDeniedException {
        // Токендеги user ADMIN болбосо
        if (currentUserProvider.getCurrentUser().getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Admin only");
        }
    }
}
