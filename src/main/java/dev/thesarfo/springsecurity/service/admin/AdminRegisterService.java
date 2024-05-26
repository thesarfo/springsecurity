package dev.thesarfo.springsecurity.service.admin;

import dev.thesarfo.springsecurity.model.Admin;
import dev.thesarfo.springsecurity.model.dto.request.admin.AdminRegisterRequest;

public interface AdminRegisterService {

    Admin registerAdmin(final AdminRegisterRequest adminRegisterRequest);

}
