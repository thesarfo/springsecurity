package dev.thesarfo.springsecurity.service.admin;

import dev.thesarfo.springsecurity.model.dto.request.TokenInvalidateRequest;

public interface AdminLogoutService {

    void logout(final TokenInvalidateRequest tokenInvalidateRequest);
}
