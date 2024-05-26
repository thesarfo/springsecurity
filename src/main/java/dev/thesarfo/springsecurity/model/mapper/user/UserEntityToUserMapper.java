package dev.thesarfo.springsecurity.model.mapper.user;

import dev.thesarfo.springsecurity.model.User;
import dev.thesarfo.springsecurity.model.entity.user.UserEntity;
import dev.thesarfo.springsecurity.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityToUserMapper extends BaseMapper<UserEntity, User> {

    @Override
    User map(UserEntity source);

    static UserEntityToUserMapper initialize() {
        return Mappers.getMapper(UserEntityToUserMapper.class);
    }

}
