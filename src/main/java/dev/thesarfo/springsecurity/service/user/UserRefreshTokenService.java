package dev.thesarfo.springsecurity.service.user;

import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenRefreshRequest;

public interface UserRefreshTokenService {

    Token refreshToken(final TokenRefreshRequest tokenRefreshRequest);

}
