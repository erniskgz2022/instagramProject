package java19.instagramproject.repo;

import java19.instagramproject.dto.userDto.response.UserListResponse;
import java19.instagramproject.dto.userDto.response.UserResponse;
import java19.instagramproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
    @Query("""
        select new java19.instagramproject.dto.userDto.response.UserListResponse
        (u.userName,ui.fullName, ui.image,size(f.subscribers),size(f.subscriptions)) from User u join u.userInfo ui join u.follower f
""")
    List<UserListResponse> getUsers();

    @Query("""
    select u from User u where lower(u.userName) like lower(concat('%', :word, '%'))
    or lower(u.userInfo.fullName) like  lower(concat('%', :word, '%'))
""")

    List<User> search(String word);
    Optional<User> findUserByEmail(String email);

}
