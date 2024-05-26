package dev.thesarfo.springsecurity.service.impl.user;


import dev.thesarfo.springsecurity.base.AbstractBaseServiceTest;
import dev.thesarfo.springsecurity.builder.UserEntityBuilder;
import dev.thesarfo.springsecurity.exception.auth.PasswordNotValidException;
import dev.thesarfo.springsecurity.exception.user.UserNotFoundException;
import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.LoginRequest;
import dev.thesarfo.springsecurity.model.entity.user.UserEntity;
import dev.thesarfo.springsecurity.repository.user.UserRepository;
import dev.thesarfo.springsecurity.service.token.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class UserLoginServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private UserLoginServiceImpl userLoginService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Test
    void login_ValidCredentials_ReturnsToken() {

        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        UserEntity userEntity = new UserEntityBuilder().withValidFields().build();

        Token expectedToken = Token.builder()
                .accessToken("mockAccessToken")
                .accessTokenExpiresAt(123456789L)
                .refreshToken("mockRefreshToken")
                .build();

        // When
        when(userRepository.findUserEntityByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(userEntity));

        when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword()))
                .thenReturn(true);

        when(tokenService.generateToken(userEntity.getClaims())).thenReturn(expectedToken);

        Token actualToken = userLoginService.login(loginRequest);

        // Then
        assertEquals(expectedToken.getAccessToken(), actualToken.getAccessToken());
        assertEquals(expectedToken.getRefreshToken(), actualToken.getRefreshToken());
        assertEquals(expectedToken.getAccessTokenExpiresAt(), actualToken.getAccessTokenExpiresAt());

        // Verify
        verify(userRepository).findUserEntityByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getPassword(), userEntity.getPassword());
        verify(tokenService).generateToken(userEntity.getClaims());

    }

    @Test
    void login_InvalidEmail_ThrowsAdminNotFoundException() {

        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("nonexistent@example.com")
                .password("password123")
                .build();

        // When
        when(userRepository.findUserEntityByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.empty());

        // Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userLoginService.login(loginRequest));

        assertEquals("User not found!\n Can't find with given email: " + loginRequest.getEmail(), exception.getMessage());

        // Verify
        verify(userRepository).findUserEntityByEmail(loginRequest.getEmail());
        verifyNoInteractions(passwordEncoder, tokenService);

    }

    @Test
    void login_InvalidPassword_ThrowsPasswordNotValidException() {

        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("invalidPassword")
                .build();

        UserEntity userEntity = new UserEntityBuilder()
                .withEmail(loginRequest.getEmail())
                .withPassword("encodedPassword")
                .build();

        // When
        when(userRepository.findUserEntityByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(userEntity));

        when(passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword()))
                .thenReturn(false);

        // Then
        PasswordNotValidException exception = assertThrows(PasswordNotValidException.class,
                () -> userLoginService.login(loginRequest));

        assertNotNull(exception);

        // Verify
        verify(userRepository).findUserEntityByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getPassword(), userEntity.getPassword());
        verifyNoInteractions(tokenService);

    }

}