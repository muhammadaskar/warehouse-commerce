package com.ecommerce.app.order.application.middleware;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.app.common.application.security.annotation.RequiresRole;
import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.common.domain.valueobject.UserId;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.order.application.service.config.OrderConfigData;
import com.ecommerce.app.order.application.service.ports.input.service.UserApplicationService;
import com.ecommerce.app.order.domain.core.entity.User;
import com.ecommerce.app.order.domain.core.exception.UserForbidden;
import com.ecommerce.app.order.domain.core.exception.UserUnauthorized;
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
    private final UserApplicationService userApplicationService;
    private final OrderConfigData orderConfigData;

    public JwtInterceptor(GlobalVerifyJWT globalVerifyJWT, UserApplicationService userApplicationService, OrderConfigData orderConfigData) {
        this.globalVerifyJWT = globalVerifyJWT;
        this.userApplicationService = userApplicationService;
        this.orderConfigData = orderConfigData;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UserUnauthorized("Unauthorized: Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);
        DecodedJWT decodedJWT = globalVerifyJWT.verifyJWT(token, orderConfigData.getSecretKey());
        if (decodedJWT == null) {
            throw new UserUnauthorized("Unauthorized: Invalid token");
        }

        String userId = decodedJWT.getSubject();
        User user = userApplicationService.checkActiveUser(userId);

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

        user.setId(new UserId(UUID.fromString(userId)));

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
