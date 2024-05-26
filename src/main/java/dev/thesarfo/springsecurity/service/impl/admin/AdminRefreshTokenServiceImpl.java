package dev.thesarfo.springsecurity.service.impl.admin;


import dev.thesarfo.springsecurity.exception.admin.AdminNotFoundException;
import dev.thesarfo.springsecurity.exception.user.UserStatusNotValidException;
import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.request.token.TokenRefreshRequest;
import dev.thesarfo.springsecurity.model.entity.admin.AdminEntity;
import dev.thesarfo.springsecurity.model.enums.TokenClaims;
import dev.thesarfo.springsecurity.model.enums.UserStatus;
import dev.thesarfo.springsecurity.repository.admin.AdminRepository;
import dev.thesarfo.springsecurity.service.token.TokenService;
import dev.thesarfo.springsecurity.service.admin.AdminRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRefreshTokenServiceImpl implements AdminRefreshTokenService {

    private final AdminRepository adminRepository;
    private final TokenService tokenService;

    @Override
    public Token refreshToken(TokenRefreshRequest tokenRefreshRequest) {

        tokenService.verifyAndValidate(tokenRefreshRequest.getRefreshToken());

        final String adminId = tokenService
                .getPayload(tokenRefreshRequest.getRefreshToken())
                .get(TokenClaims.USER_ID.getValue())
                .toString();

        final AdminEntity adminEntityFromDB = adminRepository
                .findById(adminId)
                .orElseThrow(AdminNotFoundException::new);

        this.validateAdminStatus(adminEntityFromDB);

        return tokenService.generateToken(
                adminEntityFromDB.getClaims(),
                tokenRefreshRequest.getRefreshToken()
        );

    }

    private void validateAdminStatus(final AdminEntity adminEntity) {
        if (!(UserStatus.ACTIVE.equals(adminEntity.getUserStatus()))) {
            throw new UserStatusNotValidException("UserStatus = " + adminEntity.getUserStatus());
        }
    }

}
