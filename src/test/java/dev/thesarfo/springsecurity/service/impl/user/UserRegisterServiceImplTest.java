package dev.thesarfo.springsecurity.service.impl.user;


import dev.thesarfo.springsecurity.base.AbstractBaseServiceTest;
import dev.thesarfo.springsecurity.exception.user.UserAlreadyExistException;
import dev.thesarfo.springsecurity.model.User;
import dev.thesarfo.springsecurity.model.dto.request.user.UserRegisterRequest;
import dev.thesarfo.springsecurity.model.entity.user.UserEntity;
import dev.thesarfo.springsecurity.model.mapper.user.UserEntityToUserMapper;
import dev.thesarfo.springsecurity.model.mapper.user.UserRegisterRequestToUserEntityMapper;
import dev.thesarfo.springsecurity.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserRegisterServiceImplTest extends AbstractBaseServiceTest {

    @InjectMocks
    private UserRegisterServiceImpl userRegisterService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserRegisterRequestToUserEntityMapper userRegisterRequestToUserEntityMapper = UserRegisterRequestToUserEntityMapper.initialize();

    private UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();


    @Test
    void givenUserRegisterRequest_whenRegisterUser_thenReturnUser() {

        // Given
        UserRegisterRequest request = UserRegisterRequest.builder()
                .email("test@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .build();

        String encodedPassword = "encodedPassword";

        UserEntity userEntity = userRegisterRequestToUserEntityMapper.mapForSaving(request);

        User expected = userEntityToUserMapper.map(userEntity);

        // When
        when(userRepository.existsUserEntityByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // Then
        User result = userRegisterService.registerUser(request);

        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getEmail(), result.getEmail());
        assertEquals(expected.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getLastName(), result.getLastName());

        // Verify
        verify(userRepository).save(any(UserEntity.class));

    }

    @Test
    void givenUserRegisterRequest_whenEmailAlreadyExists_thenThrowUserAlreadyExistException() {

        // Given
        UserRegisterRequest request = UserRegisterRequest.builder()
                .email("test@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .build();

        // When
        when(userRepository.existsUserEntityByEmail(request.getEmail())).thenReturn(true);

        // Then
        assertThrows(UserAlreadyExistException.class, () -> userRegisterService.registerUser(request));

        // Verify
        verify(userRepository, never()).save(any(UserEntity.class));

    }

}