package dev.thesarfo.springsecurity.service.impl.admin;

import dev.thesarfo.springsecurity.exception.admin.AdminNotFoundException;
import dev.thesarfo.springsecurity.exception.auth.PasswordNotValidException;
import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.LoginRequest;
import dev.thesarfo.springsecurity.model.entity.admin.AdminEntity;
import dev.thesarfo.springsecurity.repository.admin.AdminRepository;
import dev.thesarfo.springsecurity.service.token.TokenService;
import dev.thesarfo.springsecurity.service.admin.AdminLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public Token login(LoginRequest loginRequest) {

        final AdminEntity adminEntityFromDB = adminRepository
                .findAdminEntityByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new AdminNotFoundException("Can't find with given email: "
                                + loginRequest.getEmail())
                );

        if (Boolean.FALSE.equals(passwordEncoder.matches(
                loginRequest.getPassword(), adminEntityFromDB.getPassword()))) {
            throw new PasswordNotValidException();
        }

        return tokenService.generateToken(adminEntityFromDB.getClaims());

    }

}

