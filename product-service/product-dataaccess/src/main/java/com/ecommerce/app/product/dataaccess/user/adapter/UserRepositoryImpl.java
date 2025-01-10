package com.ecommerce.app.product.dataaccess.user.adapter;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.product.application.service.ports.output.UserRepository;
import com.ecommerce.app.product.dataaccess.user.mapper.UserDataAccessMapper;
import com.ecommerce.app.product.dataaccess.user.repository.UserJpaRepository;
import com.ecommerce.app.product.domain.core.entity.User;
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
    public Optional<User> findByID(UserId userId) {
        return userJpaRepository.findById(userId.getValue()).map(userDataAccessMapper::userEntityToUser);
    }
}
