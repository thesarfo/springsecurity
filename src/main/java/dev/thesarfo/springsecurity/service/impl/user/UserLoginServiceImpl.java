package dev.thesarfo.springsecurity.service.impl.user;

import dev.thesarfo.springsecurity.exception.auth.PasswordNotValidException;
import dev.thesarfo.springsecurity.exception.user.UserNotFoundException;
import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.LoginRequest;
import dev.thesarfo.springsecurity.model.entity.user.UserEntity;
import dev.thesarfo.springsecurity.repository.user.UserRepository;
import dev.thesarfo.springsecurity.service.token.TokenService;
import dev.thesarfo.springsecurity.service.user.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @Override
    public Token login(LoginRequest loginRequest) {

        final UserEntity userEntityFromDB = userRepository
                .findUserEntityByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("Can't find with given email: "
                                + loginRequest.getEmail())
                );

        if (Boolean.FALSE.equals(passwordEncoder.matches(
                loginRequest.getPassword(), userEntityFromDB.getPassword()))) {
            throw new PasswordNotValidException();
        }

        return tokenService.generateToken(userEntityFromDB.getClaims());

    }

}
