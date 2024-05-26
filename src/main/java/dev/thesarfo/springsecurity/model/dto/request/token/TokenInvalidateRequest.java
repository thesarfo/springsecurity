package dev.thesarfo.springsecurity.model.dto.request.token;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenInvalidateRequest {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

}
