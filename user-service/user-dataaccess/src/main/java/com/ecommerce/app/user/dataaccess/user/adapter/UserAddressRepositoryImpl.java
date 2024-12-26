package com.ecommerce.app.user.dataaccess.user.adapter;

import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.application.service.dto.create.ListUserAddressResponse;
import com.ecommerce.app.user.application.service.ports.output.repository.UserAddressRepository;
import com.ecommerce.app.user.dataaccess.user.mapper.UserAddressDataAccessMapper;
import com.ecommerce.app.user.dataaccess.user.repository.UserAddressJpaRepository;
import com.ecommerce.app.user.domain.core.entity.UserAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserAddressRepositoryImpl implements UserAddressRepository {

    private final UserAddressJpaRepository userAddressJpaRepository;
    private final UserAddressDataAccessMapper userAddressDataAccessMapper;

    public UserAddressRepositoryImpl(UserAddressJpaRepository userAddressJpaRepository, UserAddressDataAccessMapper userAddressDataAccessMapper) {
        this.userAddressJpaRepository = userAddressJpaRepository;
        this.userAddressDataAccessMapper = userAddressDataAccessMapper;
    }

    @Override
    public UserAddress save(UserAddress userAddress) {
        return userAddressDataAccessMapper.addressEntityToAddress(
                userAddressJpaRepository.save(userAddressDataAccessMapper.addressToAddressEntity(userAddress)));
    }

    @Override
    public Optional<UserAddress> findAddressByUserId(UserId userId) {
        return userAddressJpaRepository.findFirstAddressByUserId(userId.getValue())
                .map(userAddressDataAccessMapper::addressEntityToAddress);
    }

    @Override
    public List<ListUserAddressResponse> findAllAddressesByUserId(UserId userId) {
        return userAddressJpaRepository.findAllAddressesByUserId(userId.getValue())
                .stream()
                .map(userAddressDataAccessMapper::addressEntityToListUserAddressResponse)
                .toList();
    }
}
