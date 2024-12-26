package com.ecommerce.app.user.dataaccess.user.adapter;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.application.service.ports.output.repository.UserRepository;
import com.ecommerce.app.user.dataaccess.user.mapper.UserDataAccessMapper;
import com.ecommerce.app.user.dataaccess.user.repository.UserJpaRepository;
import com.ecommerce.app.user.domain.core.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserDataAccessMapper userDataAccessMapper;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserDataAccessMapper userDataAccessMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userDataAccessMapper = userDataAccessMapper;
    }

    @Override
    public User save(User user) {
        return userDataAccessMapper.userEntityToUser(userJpaRepository.save(userDataAccessMapper.userToUserEntity(user)));
    }

    @Override
    public List<User> findAll() {
    return userJpaRepository.findAll().stream()
            .map(userDataAccessMapper::userEntityToUser)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return userJpaRepository.findById(userId.getValue()).map(userDataAccessMapper::userEntityToUserWithPassword);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(userDataAccessMapper::userEntityToUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(userDataAccessMapper::userLoginEntityToUser);
    }
}
