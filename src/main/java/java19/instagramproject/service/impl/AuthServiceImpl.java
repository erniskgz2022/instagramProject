package java19.instagramproject.service.impl;

import jakarta.transaction.Transactional;
import java19.instagramproject.config.jwt.JwtService;
import java19.instagramproject.dto.userDto.request.SignInRequest;
import java19.instagramproject.dto.userDto.request.SignUpRequest;
import java19.instagramproject.dto.userDto.response.AuthSigInResponse;
import java19.instagramproject.dto.userDto.response.AuthSigUpResponse;
import java19.instagramproject.entity.Follower;
import java19.instagramproject.entity.User;
import java19.instagramproject.enums.Role;
import java19.instagramproject.repo.FollowerRepo;
import java19.instagramproject.repo.UserRepo;
import java19.instagramproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final FollowerRepo followerRepo;

    @Override
    public AuthSigUpResponse signUp(SignUpRequest request) {

        if (userRepo.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists!");
        }
        if (userRepo.existsByUserName(request.userName())) {
            throw new RuntimeException("Username already exists!");
        }
        if (!request.phoneNumber().startsWith("+996")) {
            throw new RuntimeException("Phone number must start with +996");
        }

        User user = new User();
        user.setUserName(request.userName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setPhoneNumber(request.phoneNumber());

        User savedUser = userRepo.save(user);

        Follower follower = new Follower();
        follower.setUser(savedUser);
        follower.setSubscribers(new ArrayList<>());
        follower.setSubscriptions(new ArrayList<>());
        followerRepo.save(follower);



        return AuthSigUpResponse.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .build();
    }

    @Override
    public AuthSigInResponse signIn(SignInRequest request) {
        User user;

        if (request.login().contains("@")){
            user = userRepo.findUserByEmail(request.login())
                    .orElseThrow(() -> new RuntimeException("Email not found"));
        }else {
            user = userRepo.findByUserName(request.login())
                    .orElseThrow(() -> new RuntimeException("Username not found"));
        }
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw  new BadCredentialsException("Invalid username or password");
        }
        String token = jwtService.generateToken(user);
        return AuthSigInResponse
                .builder()
                .id(user.getId())
                .userName(user.getUserName())
                .token(token)
                .build();
    }
}
