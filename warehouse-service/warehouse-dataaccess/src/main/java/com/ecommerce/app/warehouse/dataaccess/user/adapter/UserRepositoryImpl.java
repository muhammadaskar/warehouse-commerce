package com.ecommerce.app.warehouse.dataaccess.user.adapter;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.warehouse.dataaccess.user.mapper.UserDataAccessMapper;
import com.ecommerce.app.warehouse.dataaccess.user.repository.UserJpaRepository;
import com.ecommerce.app.warehouse.domain.core.entity.User;
import com.ecommerce.app.warehouse.domain.service.ports.output.repository.UserRepository;
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
    public Optional<User> findById(UserId userId) {
        return userJpaRepository.findById(userId.getValue()).map(userDataAccessMapper::userEntityToUser);
    }
}
