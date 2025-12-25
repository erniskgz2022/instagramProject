package java19.instagramproject.service.impl;

import jakarta.transaction.Transactional;
import java19.instagramproject.config.security.AccessGuard;
import java19.instagramproject.dto.postDto.response.PostResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userDto.request.SignUpRequest;
import java19.instagramproject.dto.userDto.response.UserByIdResponse;
import java19.instagramproject.dto.userDto.response.UserListResponse;
import java19.instagramproject.dto.userDto.response.UserResponse;
import java19.instagramproject.entity.*;
import java19.instagramproject.repo.UserRepo;
import java19.instagramproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo  userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AccessGuard accessGuard;

    @Override
    public List<UserListResponse> getUsers() throws AccessDeniedException {
        accessGuard.admin();
        return userRepo.getUsers();
    }

    @Override
    public UserResponse userProfile(Long id) {

        User user = findUserById(id);

        List<PostResponse> posts = user.getPosts().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .createdAt(post.getCreatedAt())
                        .images(post.getImages().stream().map(Image::getImageUrl).toList())
                        .likeCount(post.getLikes().size())
                        .taggedUsers(post.getTaggedUsers().stream().map(User::getUserName).toList())
                        .build()
                )
                .toList();

        return UserResponse.builder()
                .username(user.getUserName())
                .fullName(user.getUserInfo().getFullName())
                .image(user.getUserInfo().getImage())
                .subscribers(user.getFollower().getSubscribers().size())
                .subscriptions(user.getFollower().getSubscriptions().size())
                .posts(posts)
                .build();
    }



    @Override
    public User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    @Override
    public UserByIdResponse getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return UserByIdResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public SimpleResponse update(Long id, SignUpRequest request) throws AccessDeniedException {

        accessGuard.allow(id);

        User user = findUserById(id);
        if(!request.phoneNumber().startsWith("+996")) {
            return new SimpleResponse(HttpStatus.BAD_REQUEST, "Phone number must start with +996!");
        }

        user.setUserName(request.userName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        userRepo.save(user);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("User successfully updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) throws AccessDeniedException {
        if(!userRepo.existsById(id)) {
            return new SimpleResponse(HttpStatus.BAD_REQUEST, "User not found!");
        }
        accessGuard.admin();
        userRepo.deleteById(id);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("User successfully deleted!")
                .build();
    }
}
