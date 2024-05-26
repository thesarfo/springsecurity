package dev.thesarfo.springsecurity.controller.auth;

import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.LoginRequest;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenInvalidateRequest;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenRefreshRequest;
import dev.thesarfo.springsecurity.model.dto.request.user.UserRegisterRequest;
import dev.thesarfo.springsecurity.model.dto.response.CustomResponse;
import dev.thesarfo.springsecurity.model.dto.response.token.TokenResponse;
import dev.thesarfo.springsecurity.model.mapper.TokenToTokenResponseMapper;
import dev.thesarfo.springsecurity.service.user.UserLoginService;
import dev.thesarfo.springsecurity.service.user.UserLogoutService;
import dev.thesarfo.springsecurity.service.user.UserRefreshTokenService;
import dev.thesarfo.springsecurity.service.user.UserRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication/user")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserRegisterService userRegisterService;

    private final UserLoginService userLoginService;

    private final UserRefreshTokenService userRefreshTokenService;

    private final UserLogoutService userLogoutService;

    private TokenToTokenResponseMapper tokenToTokenResponseMapper = TokenToTokenResponseMapper.initialize();

    @PostMapping("/register")
    public CustomResponse<Void> registerUser(@RequestBody @Valid final UserRegisterRequest userRegisterRequest) {
        userRegisterService.registerUser(userRegisterRequest);
        return CustomResponse.SUCCESS;
    }

    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final LoginRequest loginRequest) {
        final Token token = userLoginService.login(loginRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest) {
        final Token token = userRefreshTokenService.refreshToken(tokenRefreshRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyAuthority('USER')")
    public CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest) {
        userLogoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }

}

