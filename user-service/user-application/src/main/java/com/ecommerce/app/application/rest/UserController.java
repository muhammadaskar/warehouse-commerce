package com.ecommerce.app.application.rest;

import com.ecommerce.app.common.application.security.annotation.RequiresRole;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.user.application.service.dto.create.*;
import com.ecommerce.app.user.application.service.ports.input.service.UserAddressApplicationService;
import com.ecommerce.app.user.application.service.ports.input.service.UserApplicationService;
import com.ecommerce.app.user.domain.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
public class UserController {
    private final UserApplicationService userApplicationService;
    private final UserAddressApplicationService userAddressApplicationService;

    public UserController(UserApplicationService userApplicationService, UserAddressApplicationService userAddressApplicationService) {
        this.userApplicationService = userApplicationService;
        this.userAddressApplicationService = userAddressApplicationService;
    }

    @RequiresRole({UserRole.SUPER_ADMIN, UserRole.WAREHOUSE_ADMIN})
    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userApplicationService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "register")
    public ResponseEntity<CreateUserResponse> createUserResponse(@RequestBody CreateUserCommand createUserCommand) {
        CreateUserResponse createUserResponse = userApplicationService.createUser(createUserCommand);
        return ResponseEntity.created(null).body(createUserResponse);
    }

    @PostMapping(value = "verify-email")
    public ResponseEntity<CreateVerifyEmailResponse> createVerifyEmail(@RequestBody CreateVerifyEmailCommand createVerifyEmailCommand) {
        CreateVerifyEmailResponse createVerifyEmailResponse = userApplicationService.createVerifyEmail(createVerifyEmailCommand);
        return ResponseEntity.ok().body(createVerifyEmailResponse);
    }

    @PostMapping(value = "login")
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody LoginUserCommand loginUserCommand) {
        LoginUserResponse loginUserResponse = userApplicationService.loginUser(loginUserCommand);
        return ResponseEntity.ok().body(loginUserResponse);
    }

    @RequiresRole({UserRole.SUPER_ADMIN})
    @PostMapping(value = "create-warehouse-admin")
    public ResponseEntity<CreateWarehouseAdminResponse> createWarehouseAdmin(@RequestBody CreateWarehouseAdminCommand createWarehouseAdminCommand) {
        CreateWarehouseAdminResponse createWarehouseAdminResponse = userApplicationService.createWarehouseAdmin(createWarehouseAdminCommand);
        return ResponseEntity.created(null).body(createWarehouseAdminResponse);
    }

    @PutMapping(value = "/{userId}/create-password")
    public ResponseEntity<CreatePasswordResponse> createPassword(
            @PathVariable("userId") UUID userId,
            @RequestBody CreatePasswordCommand createPasswordCommand) {
        log.info("Creating password for user with id: {}", userId);
        CreatePasswordResponse createPasswordResponse = userApplicationService.createPassword(
                UserIdQuery.builder().userId(userId).build(),
                createPasswordCommand);
        return ResponseEntity.ok().body(createPasswordResponse);
    }

    @PostMapping(value = "add-address")
    public ResponseEntity<CreateUserAddressResponse> createUserAddress(@RequestBody CreateUserAddressCommand createUserAddressCommand) {
        CreateUserAddressResponse createUserAddressResponse = userAddressApplicationService.addAddress(createUserAddressCommand);
        return ResponseEntity.ok().body(createUserAddressResponse);
    }

    @GetMapping(value = "/{userId}/list-address")
    public ResponseEntity<List<ListUserAddressResponse> > listUserAddress(@PathVariable("userId") UUID userId) {
        List<ListUserAddressResponse> userAddresses = userAddressApplicationService.getListAddressByUserId(UserIdQuery.builder().userId(userId).build());
        return ResponseEntity.ok().body(userAddresses);
    }
}
