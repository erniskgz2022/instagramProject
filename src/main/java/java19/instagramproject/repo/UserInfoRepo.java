package java19.instagramproject.repo;

import java19.instagramproject.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findByUserId(Long userId);
}
