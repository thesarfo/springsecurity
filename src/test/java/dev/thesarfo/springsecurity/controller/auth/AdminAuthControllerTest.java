package dev.thesarfo.springsecurity.controller.auth;

import dev.thesarfo.springsecurity.base.AbstractRestControllerTest;
import dev.thesarfo.springsecurity.model.Admin;
import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.LoginRequest;
import dev.thesarfo.springsecurity.model.dto.request.admin.AdminRegisterRequest;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenInvalidateRequest;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenRefreshRequest;
import dev.thesarfo.springsecurity.model.dto.response.CustomResponse;
import dev.thesarfo.springsecurity.model.dto.response.token.TokenResponse;
import dev.thesarfo.springsecurity.model.mapper.TokenToTokenResponseMapper;
import dev.thesarfo.springsecurity.service.admin.AdminLoginService;
import dev.thesarfo.springsecurity.service.admin.AdminLogoutService;
import dev.thesarfo.springsecurity.service.admin.AdminRefreshTokenService;
import dev.thesarfo.springsecurity.service.admin.AdminRegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminAuthControllerTest extends AbstractRestControllerTest {

    @MockBean
    private AdminRegisterService adminRegisterService;

    @MockBean
    private AdminLoginService adminLoginService;

    @MockBean
    private AdminRefreshTokenService adminRefreshTokenService;

    @MockBean
    private AdminLogoutService adminLogoutService;

    private TokenToTokenResponseMapper tokenToTokenResponseMapper = TokenToTokenResponseMapper.initialize();


    @Test
    void givenValidAdminRegisterRequest_whenRegisterAdmin_thenSuccess() throws Exception {

        // Given
        AdminRegisterRequest adminRegisterRequest = AdminRegisterRequest.builder()
                .email("admin@example.com")
                .password("password")
                .firstName("Admin")
                .lastName("User")
                .phoneNumber("12345678910")
                .build();

        Admin mockAdmin = Admin.builder()
                .id(UUID.randomUUID().toString())
                .email(adminRegisterRequest.getEmail())
                .firstName(adminRegisterRequest.getFirstName())
                .lastName(adminRegisterRequest.getLastName())
                .phoneNumber(adminRegisterRequest.getPhoneNumber())
                .password(adminRegisterRequest.getPassword())
                .build();

        // When
        when(adminRegisterService.registerAdmin(any(AdminRegisterRequest.class))).thenReturn(mockAdmin);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authentication/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminRegisterRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CustomResponse.SUCCESS)));

        // Verify
        verify(adminRegisterService, times(1)).registerAdmin(any(AdminRegisterRequest.class));

    }

    @Test
    void givenLoginRequest_WhenLoginForAdmin_ThenReturnToken() throws Exception {

        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("admin@example.com")
                .password("password")
                .build();

        Token mockToken = Token.builder()
                .accessToken("mockAccessToken")
                .accessTokenExpiresAt(3600L)
                .refreshToken("mockRefreshToken")
                .build();

        TokenResponse expectedTokenResponse = tokenToTokenResponseMapper.map(mockToken);

        // When
        when(adminLoginService.login(any(LoginRequest.class))).thenReturn(mockToken);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authentication/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.accessToken").value(expectedTokenResponse.getAccessToken()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.accessTokenExpiresAt").value(expectedTokenResponse.getAccessTokenExpiresAt()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.refreshToken").value(expectedTokenResponse.getRefreshToken()));

        // Verify
        verify(adminLoginService, times(1)).login(any(LoginRequest.class));

    }


    @Test
    void givenTokenRefreshRequest_WhenRefreshTokenForAdmin_ThenReturnTokenResponse() throws Exception {

        // Given
        TokenRefreshRequest tokenRefreshRequest = new TokenRefreshRequest("refreshToken");

        Token mockToken = Token.builder()
                .accessToken("mockAccessToken")
                .accessTokenExpiresAt(3600L)
                .refreshToken("mockRefreshToken")
                .build();

        TokenResponse expectedTokenResponse = tokenToTokenResponseMapper.map(mockToken);

        // When
        when(adminRefreshTokenService.refreshToken(any(TokenRefreshRequest.class))).thenReturn(mockToken);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authentication/admin/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenRefreshRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.accessToken").value(expectedTokenResponse.getAccessToken()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.accessTokenExpiresAt").value(expectedTokenResponse.getAccessTokenExpiresAt()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.refreshToken").value(expectedTokenResponse.getRefreshToken()));

        // Verify
        verify(adminRefreshTokenService, times(1)).refreshToken(any(TokenRefreshRequest.class));

    }


    @Test
    void givenTokenInvalidateRequest_WhenLogoutForAdmin_ThenReturnInvalidateToken() throws Exception {

        // Given
        TokenInvalidateRequest tokenInvalidateRequest = TokenInvalidateRequest.builder()
                .accessToken("Bearer " + mockAdminToken.getAccessToken())
                .refreshToken(mockAdminToken.getRefreshToken())
                .build();

        // When
        doNothing().when(adminLogoutService).logout(any(TokenInvalidateRequest.class));

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authentication/admin/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + mockAdminToken.getAccessToken())
                        .content(objectMapper.writeValueAsString(tokenInvalidateRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CustomResponse.SUCCESS)));

        // Verify
        verify(adminLogoutService, times(1)).logout(any(TokenInvalidateRequest.class));

    }

}