package com.ecommerce.app.user.application.service;

import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminCommand;
import com.ecommerce.app.user.application.service.mapper.UserDataMapper;
import com.ecommerce.app.user.application.service.ports.output.message.publisher.user.WarehouseAdminCreatedMessagePublisher;
import com.ecommerce.app.user.application.service.ports.output.repository.UserRepository;
import com.ecommerce.app.user.domain.core.UserDomainService;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.event.WarehouseAdminCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class WarehouseAdminCreateHelper {

    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final UserDataMapper userDataMapper;
    private final WarehouseAdminCreatedMessagePublisher warehouseAdminCreatedMessagePublisher;

    public WarehouseAdminCreateHelper(UserDomainService userDomainService, UserRepository userRepository, UserDataMapper userDataMapper, WarehouseAdminCreatedMessagePublisher warehouseAdminCreatedMessagePublisher) {
        this.userDomainService = userDomainService;
        this.userRepository = userRepository;
        this.userDataMapper = userDataMapper;
        this.warehouseAdminCreatedMessagePublisher = warehouseAdminCreatedMessagePublisher;
    }

    @Transactional
    public WarehouseAdminCreatedEvent persistWarehouseAdmin(CreateWarehouseAdminCommand createWarehouseAdminCommand) {
        User user = userDataMapper.warehouseAdminCommandToUser(createWarehouseAdminCommand);
        WarehouseAdminCreatedEvent warehouseAdminCreatedEvent = userDomainService.warehouseAdminCreated(user, warehouseAdminCreatedMessagePublisher);
        saveUser(user);
        log.info("Warehouse admin created with id {}", user.getId().getValue());
        return warehouseAdminCreatedEvent;
    }

    private User saveUser(User user) {
        User userResult = userRepository.save(user);
        if (userResult == null) {
            log.error("could not save user");
        }
        return userResult;
    }
}
