package dev.thesarfo.springsecurity.service.user;

import dev.thesarfo.springsecurity.model.User;
import dev.thesarfo.springsecurity.model.dto.request.user.UserRegisterRequest;

public interface UserRegisterService {

    User registerUser(final UserRegisterRequest userRegisterRequest);

}
