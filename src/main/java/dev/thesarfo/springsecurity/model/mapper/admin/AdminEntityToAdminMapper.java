package dev.thesarfo.springsecurity.model.mapper.admin;


import dev.thesarfo.springsecurity.model.Admin;
import dev.thesarfo.springsecurity.model.entity.admin.AdminEntity;
import dev.thesarfo.springsecurity.model.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminEntityToAdminMapper extends BaseMapper<AdminEntity, Admin> {

    @Override
    Admin map(AdminEntity source);

    static AdminEntityToAdminMapper initialize() {
        return Mappers.getMapper(AdminEntityToAdminMapper.class);
    }

}
