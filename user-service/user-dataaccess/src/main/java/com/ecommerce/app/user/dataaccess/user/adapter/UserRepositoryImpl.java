package com.ecommerce.app.user.dataaccess.user.adapter;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.application.service.ports.output.repository.UserRepository;
import com.ecommerce.app.user.dataaccess.user.mapper.UserDataAccessMapper;
import com.ecommerce.app.user.dataaccess.user.repository.UserJpaRepository;
import com.ecommerce.app.user.domain.core.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public Optional<User> findById(UserId userId) {
        return userJpaRepository.findById(userId.getValue()).map(userDataAccessMapper::userEntityToUser);
    }

}
