package dev.thesarfo.springsecurity.controller.auth;

import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.admin.AdminRegisterRequest;
import dev.thesarfo.springsecurity.model.dto.request.LoginRequest;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenInvalidateRequest;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenRefreshRequest;
import dev.thesarfo.springsecurity.model.dto.response.CustomResponse;
import dev.thesarfo.springsecurity.model.dto.response.token.TokenResponse;
import dev.thesarfo.springsecurity.model.mapper.TokenToTokenResponseMapper;
import dev.thesarfo.springsecurity.service.admin.AdminLoginService;
import dev.thesarfo.springsecurity.service.admin.AdminLogoutService;
import dev.thesarfo.springsecurity.service.admin.AdminRefreshTokenService;
import dev.thesarfo.springsecurity.service.admin.AdminRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminRegisterService adminRegisterService;
    private final AdminLoginService adminLoginService;
    private final AdminRefreshTokenService adminRefreshTokenService;
    private final AdminLogoutService adminLogoutService;

    private TokenToTokenResponseMapper tokenToTokenResponseMapper = TokenToTokenResponseMapper.initialize();

    @PostMapping("/register")
    public CustomResponse<Void> registerAdmin(@RequestBody @Valid final AdminRegisterRequest adminRegisterRequest) {
        adminRegisterService.registerAdmin(adminRegisterRequest);
        return CustomResponse.SUCCESS;
    }

    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginAdmin(@RequestBody @Valid final LoginRequest loginRequest) {
        final Token token = adminLoginService.login(loginRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest) {
        final Token token = adminRefreshTokenService.refreshToken(tokenRefreshRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest) {
        adminLogoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }

}
