package dev.thesarfo.springsecurity.model.mapper;

import dev.thesarfo.springsecurity.model.Token;
import dev.thesarfo.springsecurity.model.dto.response.token.TokenResponse;
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