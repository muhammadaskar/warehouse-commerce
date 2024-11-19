package com.ecommerce.app.application.rest;

import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminCommand;
import com.ecommerce.app.user.application.service.dto.create.CreateWarehouseAdminResponse;
import com.ecommerce.app.user.application.service.ports.input.service.UserApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
public class UserController {
    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping(value = "create-warehouse-admin")
    public ResponseEntity<CreateWarehouseAdminResponse> createWarehouseAdmin(@RequestBody CreateWarehouseAdminCommand createWarehouseAdminCommand) {
        CreateWarehouseAdminResponse createWarehouseAdminResponse = userApplicationService.createWarehouseAdmin(createWarehouseAdminCommand);
        return ResponseEntity.ok().body(createWarehouseAdminResponse);
    }

    @GetMapping
    public ResponseEntity<String> getList() {
        return ResponseEntity.ok("Hello World");
    }
}
