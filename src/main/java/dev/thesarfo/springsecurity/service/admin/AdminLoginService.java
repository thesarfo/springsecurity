package dev.thesarfo.springsecurity.service.admin;


import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.LoginRequest;

public interface AdminLoginService {

    Token login(final LoginRequest loginRequest);

}

