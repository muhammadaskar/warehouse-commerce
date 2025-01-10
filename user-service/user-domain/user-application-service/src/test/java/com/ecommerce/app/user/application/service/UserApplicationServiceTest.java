package com.ecommerce.app.user.application.service;


import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.user.application.service.dto.create.LoginUserCommand;
import com.ecommerce.app.user.application.service.dto.create.LoginUserResponse;
import com.ecommerce.app.user.application.service.mapper.UserDataMapper;
import com.ecommerce.app.user.application.service.ports.input.service.UserApplicationService;
import com.ecommerce.app.user.application.service.ports.output.repository.UserRepository;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = UserTestConfiguration.class)
public class UserApplicationServiceTest {

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private UserRepository userRepository;

    private LoginUserCommand loginUserCommand;
    private LoginUserCommand loginUserCommandWrongEmail;

    private User user;

    @BeforeAll
    void init() {
        loginUserCommand = LoginUserCommand.builder()
                .email("askar@gmail.com")
                .password("askar123")
                .build();

        loginUserCommandWrongEmail = LoginUserCommand.builder()
                .email("askar@gmail.com")
                .password("askar123")
                .build();

        user = User.builder()
                .withId(new UserId(UUID.randomUUID()))
                .withEmail("askar@gmail.com")
                .withPassword("$2a$10$B4iIWo/gfc.mkKwR3GbWne0pt4B07LHFER7hHysugWh4CNxXowbeS")
                .withWarehouseId(null)
                .withIsEmailVerified(true)
                .withRole(UserRole.CUSTOMER)
                .build();
    }

    @Test
    void testLoginCustomer() {
        System.out.println("TestLoginCustomer");
        Mockito.when(userRepository.findByEmail(loginUserCommand.getEmail())).thenReturn(Optional.of(user));
        LoginUserResponse response = userApplicationService.loginUser(loginUserCommand);

        assertNotNull(response, "Response must not be null");
        assertNotNull(response.getEmail(), "Email must not be null");
        assertNotNull(response.getToken(), "Token must not be null");
        assertNotNull(response.getRole(), "Role must not be null");
        assertNotNull(response.getMessage(), "Message must not be null");
        assertEquals("askar@gmail.com", response.getEmail(), "Email must be same");
        assertEquals(UserRole.CUSTOMER.toString(), response.getRole(), "Role must be same");
        assertEquals("User logged in successfully!", response.getMessage(), "Message must be same");

        System.out.println(response.getEmail());
        System.out.println(response.getToken());
        System.out.println(response.getRole());
        System.out.println(response.getMessage());
    }

    @Test
    void testLoginCustomerWrongEmail() {
        System.out.println("TestLoginCustomer");
        Mockito.when(userRepository.findByEmail(loginUserCommandWrongEmail.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userApplicationService.loginUser(loginUserCommandWrongEmail));
    }
}
