package java19.instagramproject.service;

import java19.instagramproject.dto.userDto.request.SignInRequest;
import java19.instagramproject.dto.userDto.request.SignUpRequest;
import java19.instagramproject.dto.userDto.response.AuthResponse;

public interface AuthService {
    AuthResponse signUp(SignUpRequest request);
    AuthResponse signIn(SignInRequest request);
}
