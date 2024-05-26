package dev.thesarfo.springsecurity.service.user;

import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.LoginRequest;

public interface UserLoginService {

    Token login(final LoginRequest loginRequest);

}
