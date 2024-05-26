package dev.thesarfo.springsecurity.model.mapper;

import com.nimbusds.oauth2.sdk.TokenResponse;
import dev.thesarfo.springsecurity.model.Token;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenToTokenResponseMapper extends BaseMapper<Token, TokenResponse> {

    @Override
    TokenResponse map(Token source);

    static TokenToTokenResponseMapper initialize() {
        return Mappers.getMapper(TokenToTokenResponseMapper.class);
    }

}