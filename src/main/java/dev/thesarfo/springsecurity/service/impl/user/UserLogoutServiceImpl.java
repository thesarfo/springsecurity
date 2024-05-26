package dev.thesarfo.springsecurity.service.impl.user;

import dev.thesarfo.springsecurity.model.dto.request.TokenInvalidateRequest;
import dev.thesarfo.springsecurity.service.token.InvalidTokenService;
import dev.thesarfo.springsecurity.service.token.TokenService;
import dev.thesarfo.springsecurity.service.user.UserLogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserLogoutServiceImpl implements UserLogoutService {

    private final TokenService tokenService;

    private final InvalidTokenService invalidTokenService;

    @Override
    public void logout(final TokenInvalidateRequest tokenInvalidateRequest) {

        tokenService.verifyAndValidate(
                Set.of(
                        tokenInvalidateRequest.getAccessToken(),
                        tokenInvalidateRequest.getRefreshToken()
                )
        );

        final String accessTokenId = tokenService
                .getPayload(tokenInvalidateRequest.getAccessToken())
                .getId();

        invalidTokenService.checkForInvalidityOfToken(accessTokenId);


        final String refreshTokenId = tokenService
                .getPayload(tokenInvalidateRequest.getRefreshToken())
                .getId();

        invalidTokenService.checkForInvalidityOfToken(refreshTokenId);

        invalidTokenService.invalidateTokens(Set.of(accessTokenId,refreshTokenId));

    }

}
