package com.ecommerce.app.product.application.middleware;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.app.common.application.security.annotation.RequiresRole;
import com.ecommerce.app.common.application.service.GlobalVerifyJWT;
import com.ecommerce.app.common.domain.valueobject.UserRole;
import com.ecommerce.app.product.application.service.config.ProductConfigData;
import com.ecommerce.app.product.application.service.ports.input.service.UserApplicationService;
import com.ecommerce.app.product.domain.core.exception.UserForbidden;
import com.ecommerce.app.product.domain.core.exception.UserUnauthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final GlobalVerifyJWT globalVerifyJWT;
    private final UserApplicationService userApplicationService;
    private final ProductConfigData productConfigData;

    public JwtInterceptor(GlobalVerifyJWT globalVerifyJWT, UserApplicationService userApplicationService, ProductConfigData productConfigData) {
        this.globalVerifyJWT = globalVerifyJWT;
        this.userApplicationService = userApplicationService;
        this.productConfigData = productConfigData;
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
        DecodedJWT decodedJWT = globalVerifyJWT.verifyJWT(token, productConfigData.getSecretKey());
        if (decodedJWT == null) {
            throw new UserUnauthorized("Unauthorized: Invalid token");
        }

        String userId = decodedJWT.getSubject();
        userApplicationService.checkActiveUser(userId);

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
