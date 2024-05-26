package dev.thesarfo.springsecurity.service.impl.admin;

import dev.thesarfo.springsecurity.exception.admin.AdminAlreadyExistException;
import dev.thesarfo.springsecurity.model.Admin;
import dev.thesarfo.springsecurity.model.dto.request.admin.AdminRegisterRequest;
import dev.thesarfo.springsecurity.model.entity.admin.AdminEntity;
import dev.thesarfo.springsecurity.model.mapper.admin.AdminEntityToAdminMapper;
import dev.thesarfo.springsecurity.model.mapper.admin.AdminRegisterRequestToAdminEntityMapper;
import dev.thesarfo.springsecurity.repository.admin.AdminRepository;
import dev.thesarfo.springsecurity.service.admin.AdminRegisterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRegisterServiceImpl implements AdminRegisterService {

    private final AdminRepository adminRepository;
    private final AdminRegisterRequestToAdminEntityMapper adminRegisterRequestToAdminEntityMapper = AdminRegisterRequestToAdminEntityMapper.initialize();

    private final AdminEntityToAdminMapper adminEntityToAdminMapper = AdminEntityToAdminMapper.initialize();

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Admin registerAdmin(AdminRegisterRequest adminRegisterRequest) {

        if (adminRepository.existsAdminEntityByEmail(adminRegisterRequest.getEmail())) {
            throw new AdminAlreadyExistException("The email is already used for another admin : " + adminRegisterRequest.getEmail());
        }

        final AdminEntity adminEntityToBeSave = adminRegisterRequestToAdminEntityMapper.mapForSaving(adminRegisterRequest);

        adminEntityToBeSave.setPassword(passwordEncoder.encode(adminRegisterRequest.getPassword()));

        AdminEntity savedAdminEntity = adminRepository.save(adminEntityToBeSave);

        return adminEntityToAdminMapper.map(savedAdminEntity);

    }

}
