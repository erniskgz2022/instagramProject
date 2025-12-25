package java19.instagramproject.service.impl;

import jakarta.transaction.Transactional;
import java19.instagramproject.config.security.AccessGuard;
import java19.instagramproject.dto.followerDto.UserShortDto;
import java19.instagramproject.dto.followerDto.response.FollowerResponse;
import java19.instagramproject.dto.followerDto.response.SearchResponse;
import java19.instagramproject.entity.Follower;
import java19.instagramproject.entity.User;
import java19.instagramproject.repo.UserRepo;
import java19.instagramproject.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final UserRepo userRepo;
    private final AccessGuard accessGuard;
    @Override
    public List<SearchResponse> search(String word) {
        return userRepo.search(word)
                .stream()
                .map(u -> SearchResponse.builder()
                        .id(u.getId())
                        .userName(u.getUserName())
                        .fullName(u.getUserInfo() != null ? u.getUserInfo().getFullName() : null)
                        .image(u.getUserInfo() != null ? u.getUserInfo().getImage() : null)
                        .build()).toList();
    }

    @Override
    public FollowerResponse subscribe(Long myId, Long targetUserId) throws AccessDeniedException {
        if (myId.equals(targetUserId)) {
            throw new IllegalArgumentException("You can't subscribe to yourself");
        }
        accessGuard.allow(myId);
        User me = userRepo.findById(myId).orElseThrow(() -> new RuntimeException("user not found"));
        User target = userRepo.findById(targetUserId).orElseThrow(() -> new RuntimeException("target user not found"));

        Follower myFollower = me.getFollower();
        Follower targetFollower = target.getFollower();

        boolean alreadySubscribed = myFollower.getSubscriptions().contains(targetUserId);

        if (alreadySubscribed) {
            myFollower.getSubscriptions().remove(targetUserId);
            targetFollower.getSubscribers().remove(myId);
            return new   FollowerResponse ("Подписаться");
        }

        myFollower.getSubscriptions().add(targetUserId);
        targetFollower.getSubscribers().add(myId);
        return new FollowerResponse ("Отписка");
    }

    @Override
    public List<UserShortDto> getAllSubscribersByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Follower f = user.getFollower();
        return f.getSubscribers()
                .stream()
                .map(id -> {
                    User u = userRepo.findById(id).orElseThrow();
                    return UserShortDto.builder()
                            .id(u.getId())
                            .userName(u.getUserName())
                            .fullName(u.getUserInfo() != null ? u.getUserInfo().getFullName() : null)
                            .image(u.getUserInfo() != null ? u.getUserInfo().getImage() : null)
                            .build();
                }).toList();
    }


    @Override
    public List<UserShortDto> getAllSubscriptionsByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Follower f = user.getFollower();
        return f.getSubscriptions()
                .stream()
                .map(id -> {
                    User u = userRepo.findById(id).orElseThrow();
                    return UserShortDto.builder()
                            .id(u.getId())
                            .userName(u.getUserName())
                            .fullName(u.getUserInfo() != null ? u.getUserInfo().getFullName() : null)
                            .image(u.getUserInfo() != null ? u.getUserInfo().getImage() : null)
                            .build();
                }).toList();
    }

}
