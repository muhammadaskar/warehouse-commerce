package com.ecommerce.app.user.application.service;

import com.ecommerce.app.common.domain.valueobject.Address;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.user.application.service.dto.create.CreateUserAddressCommand;
import com.ecommerce.app.user.application.service.dto.create.ListUserAddressResponse;
import com.ecommerce.app.user.application.service.dto.create.UserIdQuery;
import com.ecommerce.app.user.application.service.mapper.UserAddressDataMapper;
import com.ecommerce.app.user.application.service.ports.output.repository.UserAddressRepository;
import com.ecommerce.app.user.application.service.ports.output.repository.UserRepository;
import com.ecommerce.app.user.domain.core.UserAddressDomainService;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.entity.UserAddress;
import com.ecommerce.app.user.domain.core.exception.UserException;
import com.ecommerce.app.user.domain.core.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class UserAddressHelper {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserAddressDomainService userAddressDomainService;
    private final UserAddressDataMapper userAddressDataMapper;

    public UserAddressHelper(UserRepository userRepository, UserAddressRepository userAddressRepository, UserAddressDomainService userAddressDomainService, UserAddressDataMapper userAddressDataMapper) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.userAddressDomainService = userAddressDomainService;
        this.userAddressDataMapper = userAddressDataMapper;
    }

    @Transactional
    public UserAddress addAddressToUser(CreateUserAddressCommand createUserAddressCommand) {
        log.info("Adding address to user with id: {}", createUserAddressCommand.getUserId());
        UserAddress userAddress = userAddressDataMapper.userAddressCommandToUserAddress(createUserAddressCommand);
        User user = userRepository.findById(new UserId(UUID.fromString(createUserAddressCommand.getUserId())))
                .orElseThrow(() -> new UserNotFoundException("User with id " + createUserAddressCommand.getUserId() + " could not be found!"));
        userAddressDomainService.addAddress(new UserId(user.getId().getValue()), userAddress);

        UserAddress findUserAddress = userAddressRepository.findAddressByUserId(user.getId()).orElse(null);
        if (findUserAddress == null) {
            userAddress.setPrimary(true);
        }

        saveUserAddress(userAddress);
        log.info("Address added to user with id: {}", user.getId());
        return userAddress;
    }

    @Transactional(readOnly = true)
    public List<ListUserAddressResponse> getListAddressByUserId(UserIdQuery userId) {
        log.info("Finding address by user id: {}", userId);
        List<ListUserAddressResponse> userAddresses = userAddressRepository.findAllAddressesByUserId(new UserId(userId.getUserId()));
        return userAddresses;
    }

    private UserAddress saveUserAddress(UserAddress userAddress) {
        UserAddress userAddressResult = userAddressRepository.save(userAddress);
        if (userAddressResult == null) {
            log.error("User address could not be saved!");
            throw new UserException("User address could not be saved!");
        }
        return userAddressResult;
    }
}
