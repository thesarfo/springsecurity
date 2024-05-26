package dev.thesarfo.springsecurity.service.impl.admin;

import dev.thesarfo.springsecurity.base.AbstractBaseServiceTest;
import dev.thesarfo.springsecurity.builder.AdminEntityBuilder;
import dev.thesarfo.springsecurity.builder.TokenBuilder;
import dev.thesarfo.springsecurity.exception.admin.AdminNotFoundException;
import dev.thesarfo.springsecurity.exception.user.UserStatusNotValidException;
import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenRefreshRequest;
import dev.thesarfo.springsecurity.model.entity.admin.AdminEntity;
import dev.thesarfo.springsecurity.model.enums.UserStatus;
import dev.thesarfo.springsecurity.repository.admin.AdminRepository;
import dev.thesarfo.springsecurity.service.token.TokenService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class AdminRefreshTokenServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private AdminRefreshTokenServiceImpl adminRefreshTokenService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private TokenService tokenService;


    @Test
    void refreshToken_ValidRefreshToken_ReturnsToken() {

        // Given
        final String refreshTokenString = "mockRefreshToken";
        final TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken(refreshTokenString)
                .build();

        final AdminEntity mockAdminUserEntity = new AdminEntityBuilder().withValidFields().build();

        final Claims mockClaims = TokenBuilder.getValidClaims(
                mockAdminUserEntity.getId(),
                mockAdminUserEntity.getFirstName()
        );

        final Token expectedToken = Token.builder()
                .accessToken("mockAccessToken")
                .accessTokenExpiresAt(123456789L)
                .refreshToken("newMockRefreshToken")
                .build();

        doNothing().when(tokenService).verifyAndValidate(refreshTokenString);
        when(tokenService.getPayload(refreshTokenString)).thenReturn(mockClaims);
        when(adminRepository.findById(anyString())).thenReturn(Optional.of(mockAdminUserEntity));
        when(tokenService.generateToken(mockAdminUserEntity.getClaims(), refreshTokenString)).thenReturn(expectedToken);

        // When
        Token actualToken = adminRefreshTokenService.refreshToken(tokenRefreshRequest);

        // Then
        assertNotNull(actualToken);
        assertEquals(expectedToken.getAccessToken(), actualToken.getAccessToken());
        assertEquals(expectedToken.getAccessTokenExpiresAt(), actualToken.getAccessTokenExpiresAt());
        assertEquals(expectedToken.getRefreshToken(), actualToken.getRefreshToken());

        // Verify
        verify(tokenService).verifyAndValidate(refreshTokenString);
        verify(tokenService).getPayload(refreshTokenString);
        verify(adminRepository).findById(anyString());
        verify(tokenService).generateToken(mockAdminUserEntity.getClaims(), refreshTokenString);

    }

    @Test
    void refreshToken_InvalidRefreshToken_ThrowsException() {

        // Given
        final String refreshTokenString = "invalidRefreshToken";
        final TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken(refreshTokenString)
                .build();

        // When
        doThrow(RuntimeException.class).when(tokenService).verifyAndValidate(refreshTokenString);

        // Then
        assertThrows(RuntimeException.class,
                () -> adminRefreshTokenService.refreshToken(tokenRefreshRequest));

        // Verify
        verify(tokenService).verifyAndValidate(refreshTokenString);
        verifyNoInteractions(adminRepository);

    }

    @Test
    void refreshToken_AdminNotFound_ThrowsException() {

        // Given
        final String refreshTokenString = "validRefreshToken";
        final TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken(refreshTokenString)
                .build();

        final Claims mockClaims = TokenBuilder.getValidClaims("nonExistentAdminId", "John");

        // When
        doNothing().when(tokenService).verifyAndValidate(refreshTokenString);
        when(tokenService.getPayload(refreshTokenString)).thenReturn(mockClaims);
        when(adminRepository.findById("nonExistentAdminId")).thenReturn(Optional.empty());

        // Then
        AdminNotFoundException exception = assertThrows(AdminNotFoundException.class,
                () -> adminRefreshTokenService.refreshToken(tokenRefreshRequest));

        assertEquals("""
            Admin not found!
            """, exception.getMessage());

        // Verify
        verify(tokenService).verifyAndValidate(refreshTokenString);
        verify(tokenService).getPayload(refreshTokenString);
        verify(adminRepository).findById("nonExistentAdminId");

    }

    @Test
    void refreshToken_InactiveAdmin_ThrowsException() {

        // Given
        String refreshTokenString = "validRefreshToken";
        TokenRefreshRequest tokenRefreshRequest = TokenRefreshRequest.builder()
                .refreshToken(refreshTokenString)
                .build();

        AdminEntity inactiveAdmin = new AdminEntityBuilder().withValidFields().withUserStatus(UserStatus.PASSIVE).build();

        Claims mockClaims = TokenBuilder.getValidClaims(inactiveAdmin.getId(), inactiveAdmin.getFirstName());

        // When
        doNothing().when(tokenService).verifyAndValidate(refreshTokenString);
        when(tokenService.getPayload(refreshTokenString)).thenReturn(mockClaims);
        when(adminRepository.findById(inactiveAdmin.getId())).thenReturn(Optional.of(inactiveAdmin));

        // Then
        UserStatusNotValidException exception = assertThrows(UserStatusNotValidException.class,
                () -> adminRefreshTokenService.refreshToken(tokenRefreshRequest));

        assertEquals("User status is not valid!\n UserStatus = PASSIVE", exception.getMessage());

        // Verify
        verify(tokenService).verifyAndValidate(refreshTokenString);
        verify(tokenService).getPayload(refreshTokenString);
        verify(adminRepository).findById(inactiveAdmin.getId());

    }

}