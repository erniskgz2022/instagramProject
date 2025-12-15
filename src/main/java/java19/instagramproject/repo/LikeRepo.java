package java19.instagramproject.repo;

import java19.instagramproject.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like,Long> {
    Like findByUserIdAndPostId(Long userId, Long postId);
}
