package dev.thesarfo.springsecurity.service.impl.user;

import dev.thesarfo.springsecurity.exception.user.UserAlreadyExistException;
import dev.thesarfo.springsecurity.model.User;
import dev.thesarfo.springsecurity.model.dto.request.UserRegisterRequest;
import dev.thesarfo.springsecurity.model.entity.user.UserEntity;
import dev.thesarfo.springsecurity.model.mapper.UserEntityToUserMapper;
import dev.thesarfo.springsecurity.model.mapper.UserRegisterRequestToUserEntityMapper;
import dev.thesarfo.springsecurity.repository.user.UserRepository;
import dev.thesarfo.springsecurity.service.user.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserRepository userRepository;

    private final UserRegisterRequestToUserEntityMapper userRegisterRequestToUserEntityMapper = UserRegisterRequestToUserEntityMapper.initialize();

    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserRegisterRequest userRegisterRequest) {

        if (userRepository.existsUserEntityByEmail(userRegisterRequest.getEmail())) {
            throw new UserAlreadyExistException("The email is already used for another admin : " + userRegisterRequest.getEmail());
        }

        final UserEntity userEntityToBeSave = userRegisterRequestToUserEntityMapper.mapForSaving(userRegisterRequest);

        userEntityToBeSave.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));

        UserEntity savedUserEntity = userRepository.save(userEntityToBeSave);

        return userEntityToUserMapper.map(savedUserEntity);

    }

}
