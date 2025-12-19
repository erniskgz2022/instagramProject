package java19.instagramproject.config.security;

import java19.instagramproject.entity.User;
import java19.instagramproject.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class AccessGuard {
    private final CurrentUserProvider currentUserProvider;

    public void allow(Long ownerId) throws AccessDeniedException {
        User u = currentUserProvider.getCurrentUser();
        if (u.getRole() != Role.ADMIN && !u.getId().equals(ownerId)) {
            throw new AccessDeniedException("Access denied");
        }
    }

    public void admin() throws AccessDeniedException {
        if (currentUserProvider.getCurrentUser().getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Admin only");
        }
    }
}
