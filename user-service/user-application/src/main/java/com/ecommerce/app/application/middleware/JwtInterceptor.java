package com.ecommerce.app.application.middleware;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.app.common.application.security.annotation.RequiresRole;
import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.user.application.service.config.UserServiceConfigData;
import com.ecommerce.app.user.application.service.dto.create.UserIdQuery;
import com.ecommerce.app.user.application.service.ports.input.service.UserApplicationService;
import com.ecommerce.app.user.domain.core.entity.User;
import com.ecommerce.app.user.domain.core.exception.UserException;
import com.ecommerce.app.user.domain.core.exception.UserForbidden;
import com.ecommerce.app.user.domain.core.exception.UserUnauthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.UUID;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final GlobalVerifyJWT globalVerifyJWT;
    private final UserServiceConfigData userServiceConfigData;
    private final UserApplicationService userApplicationService;

    public JwtInterceptor(GlobalVerifyJWT globalVerifyJWT, UserServiceConfigData userServiceConfigData, UserApplicationService userApplicationService) {
        this.globalVerifyJWT = globalVerifyJWT;
        this.userServiceConfigData = userServiceConfigData;
        this.userApplicationService = userApplicationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UserException("Unauthorized: Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);
        DecodedJWT decodedJWT = globalVerifyJWT.verifyJWT(token, userServiceConfigData.getSecretKey());
        if (decodedJWT == null) {
            throw new UserUnauthorized("Unauthorized: Invalid token");
        }

        UUID userId = UUID.fromString(decodedJWT.getSubject());
        User user = userApplicationService.getUserById(new UserIdQuery(userId));
        if (user == null) {
            throw new UserUnauthorized("Unauthorized: User not found");
        }

        if (!user.isEmailVerified()) {
            throw new UserException("Unauthorized: Email not verified");
        }

        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            RequiresRole requiresRole = method.getAnnotation(RequiresRole.class);
            if (requiresRole != null) {
                String role = decodedJWT.getClaim("role").asString();
                if (!isRoleValid(role, requiresRole.value())) {
                    throw new UserForbidden("Forbidden: Insufficient permissions");
                }
            }
        }

        return true;
    }

    private boolean isRoleValid(String role, UserRole[] requiredRoles) {
        for (UserRole requiredRole : requiredRoles) {
            if (role.equals(requiredRole.toString())) {
                return true;
            }
        }
        return false;
    }
}
