package dev.thesarfo.springsecurity.model.mapper.admin;

import dev.thesarfo.springsecurity.model.dto.request.AdminRegisterRequest;
import dev.thesarfo.springsecurity.model.entity.admin.AdminEntity;
import dev.thesarfo.springsecurity.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminRegisterRequestToAdminEntityMapper extends BaseMapper<AdminRegisterRequest, AdminEntity> {


    @Named("mapForSaving")
    default AdminEntity mapForSaving(AdminRegisterRequest adminRegisterRequest) {
        return AdminEntity.builder()
                .email(adminRegisterRequest.getEmail())
                .firstName(adminRegisterRequest.getFirstName())
                .lastName(adminRegisterRequest.getLastName())
                .phoneNumber(adminRegisterRequest.getPhoneNumber())
                .build();
    }

    static AdminRegisterRequestToAdminEntityMapper initialize() {
        return Mappers.getMapper(AdminRegisterRequestToAdminEntityMapper.class);
    }

}
