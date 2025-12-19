package java19.instagramproject.service.impl;

import jakarta.transaction.Transactional;
import java19.instagramproject.config.security.AccessGuard;
import java19.instagramproject.config.security.CurrentUserProvider;
import java19.instagramproject.dto.postDto.request.PostRequest;
import java19.instagramproject.dto.postDto.response.PostResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.entity.Image;
import java19.instagramproject.entity.Like;
import java19.instagramproject.entity.Post;
import java19.instagramproject.entity.User;
import java19.instagramproject.repo.PostRepo;
import java19.instagramproject.repo.UserRepo;
import java19.instagramproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final CurrentUserProvider currentUserProvider;
    private final AccessGuard accessGuard;

    @Override
    public SimpleResponse savePost( PostRequest request) throws AccessDeniedException {
        if (request.images() == null || request.images().isEmpty()) {
            throw new IllegalArgumentException("Images cannot be empty");
        }
        User currentUser = currentUserProvider.getCurrentUser();
        Post post = new Post();
        post.setTitle(request.title());
        post.setDescription(request.description());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(currentUser);

        if (request.taggedUserIds() != null) {
            for (Long id : request.taggedUserIds()) {
                User tagged = userRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Tagged user not found: " + id));
                post.getTaggedUsers().add(tagged);
            }
        }

        for (String img : request.images()) {
            Image image = new Image();
            image.setImageUrl(img);
            image.setPost(post);
            post.getImages().add(image);
        }

        Like like = new Like();
        like.setLike(false);
        like.setPost(post);
        like.setUser(currentUser);
        post.getLikes().add(like);

        postRepo.save(post);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Post Created")
                .build();
    }

    @Override
    public PostResponse getById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .images(post.getImages().stream().map(Image::getImageUrl).toList())
                .likeCount(post.getLikes().size())
                .taggedUsers(post.getTaggedUsers().stream().map(User::getUserName).toList())
                .build();
    }
    @Override
    public List<PostResponse> getAll() {
        return postRepo.findAll().stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .createdAt(post.getCreatedAt())
                        .images(post.getImages().stream().map(Image::getImageUrl).toList())
                        .likeCount(post.getLikes().size())
                        .taggedUsers(post.getTaggedUsers().stream().map(User::getUserName).toList())
                        .build())
                .toList();
    }
    @Override
    public SimpleResponse update(Long postId, PostRequest request) throws AccessDeniedException {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        accessGuard.allow(post.getUser().getId());

        post.setTitle(request.title());
        post.setDescription(request.description());

        postRepo.save(post);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Post successfully updated!")
                .build();
    }


    @Override
    public SimpleResponse delete(Long postId) throws AccessDeniedException {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        accessGuard.allow(post.getUser().getId());

        return  SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Post successfully deleted!")
                .build();
    }


    @Override
    public List<PostResponse> feed(Long userId) {
        User me = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Long> subscriptions = me.getFollower().getSubscriptions();

        List<Post> posts = postRepo.findAll().stream()
                .filter(p -> p.getUser().getId().equals(userId)
                        || subscriptions.contains(p.getUser().getId()))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .toList();

        return posts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .createdAt(post.getCreatedAt())
                        .images(post.getImages().stream().map(Image::getImageUrl).toList())
                        .likeCount(post.getLikes().size())
                        .taggedUsers(post.getTaggedUsers().stream().map(User::getUserName).toList())
                        .build())
                .toList();
    }

    @Override
    public Page<PostResponse> getAll(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts;
        if (userId == null) {
            posts = postRepo.findAll(pageable);               // bardyk posttor
        } else {
            posts = postRepo.findAllByUserId(userId, pageable); // user posttory
        }
        return posts.map(post -> PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .images(
                        post.getImages()
                                .stream()
                                .map(Image::getImageUrl)
                                .toList()
                )
                .likeCount(post.getLikes().size())
                .taggedUsers(
                        post.getTaggedUsers()
                                .stream()
                                .map(User::getUserName)
                                .toList()
                )
                .build());
    }

}

