package dev.thesarfo.springsecurity.repository.admin;

import dev.thesarfo.springsecurity.model.entity.admin.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, String> {

    boolean existsAdminEntityByEmail(final String email);

    Optional<AdminEntity> findAdminEntityByEmail(final String email);

}
