package java19.instagramproject.repo;

import java19.instagramproject.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowerRepo extends JpaRepository<Follower,Long> {
}
