package com.trklst.zuulserver.security;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class AdminValidator {

    private static final String ADMIN_PATTERN = "/api/admin";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    public boolean isUserAuthorized(Map<String, Object> checkTokenResponse) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        if (!request.getRequestURI().startsWith(ADMIN_PATTERN))
            return true;
        String role = (String) checkTokenResponse.getOrDefault("role", "ROLE_USER");
        return role.equals(ADMIN_ROLE);
    }
}
