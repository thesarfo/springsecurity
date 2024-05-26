package dev.thesarfo.springsecurity.model.mapper.user;

import dev.thesarfo.springsecurity.model.dto.request.user.UserRegisterRequest;
import dev.thesarfo.springsecurity.model.entity.user.UserEntity;
import dev.thesarfo.springsecurity.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRegisterRequestToUserEntityMapper extends BaseMapper<UserRegisterRequest, UserEntity> {

    @Named("mapForSaving")
    default UserEntity mapForSaving(UserRegisterRequest userRegisterRequest) {
        return UserEntity.builder()
                .email(userRegisterRequest.getEmail())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .build();
    }

    static UserRegisterRequestToUserEntityMapper initialize() {
        return Mappers.getMapper(UserRegisterRequestToUserEntityMapper.class);
    }

}
