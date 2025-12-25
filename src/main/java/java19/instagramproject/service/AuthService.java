package java19.instagramproject.service;

import java19.instagramproject.dto.userDto.request.SignInRequest;
import java19.instagramproject.dto.userDto.request.SignUpRequest;
import java19.instagramproject.dto.userDto.response.AuthSigInResponse;
import java19.instagramproject.dto.userDto.response.AuthSigUpResponse;

public interface AuthService {
    AuthSigUpResponse signUp(SignUpRequest request);
    AuthSigInResponse signIn(SignInRequest request);
}
