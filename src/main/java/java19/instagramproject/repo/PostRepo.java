package java19.instagramproject.repo;

import java19.instagramproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Long> {
    @Query("select p from Post p where p.user.id in :ids order by p.createdAt desc")
    List<Post> findFeed(List<Long> ids);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByUserId(Long userId, Pageable pageable);

}
