package java19.instagramproject.api;

import java19.instagramproject.dto.followerDto.UserShortDto;
import java19.instagramproject.dto.followerDto.response.FollowerResponse;
import java19.instagramproject.dto.followerDto.response.SearchResponse;
import java19.instagramproject.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/followers")
@RequiredArgsConstructor
public class FollowerApi {
    private final FollowerService followerService;

    @GetMapping("/search")
    public List<SearchResponse> search(String word){
        return followerService.search(word);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("{myId}/subscribe/{targetId}")
    public FollowerResponse subscribe (@PathVariable("myId") Long myId, @PathVariable("targetId") Long targetId){
        return followerService.subscribe(myId, targetId);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}/subscribers")
    public List<UserShortDto> subscribers(@PathVariable Long userId){
        return followerService.getAllSubscribersByUserId(userId);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}/subscriptions")
    public List<UserShortDto> subscriptions(@PathVariable Long userId){
        return followerService.getAllSubscriptionsByUserId(userId);
    }
}
