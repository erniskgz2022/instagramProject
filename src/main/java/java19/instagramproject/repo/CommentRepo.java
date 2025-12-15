package java19.instagramproject.repo;

import java19.instagramproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostId(Long postId);

}
