package dev.thesarfo.springsecurity.service.admin;


import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.TokenRefreshRequest;

public interface AdminRefreshTokenService {

    Token refreshToken(final TokenRefreshRequest tokenRefreshRequest);

}
