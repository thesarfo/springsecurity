package dev.thesarfo.springsecurity.model.mapper;

import dev.thesarfo.springsecurity.model.dto.request.UserRegisterRequest;
import dev.thesarfo.springsecurity.model.entity.user.UserEntity;
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
