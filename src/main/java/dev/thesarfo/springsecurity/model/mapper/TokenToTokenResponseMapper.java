package dev.thesarfo.springsecurity.model.mapper;

import dev.thesarfo.springsecurity.model.Token;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TokenToTokenResponseMapper extends BaseMapper<Token,dev.thesarfo.springsecurity.model.dto.response.TokenResponse> {

    @Override
    dev.thesarfo.springsecurity.model.dto.response.TokenResponse map(Token source);

    static TokenToTokenResponseMapper initialize() {
        return Mappers.getMapper(TokenToTokenResponseMapper.class);
    }

}