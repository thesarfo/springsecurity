package dev.thesarfo.springsecurity.service.user;


import dev.thesarfo.springsecurity.model.dto.request.TokenInvalidateRequest;

public interface UserLogoutService {

    void logout(final TokenInvalidateRequest tokenInvalidateRequest);

}
