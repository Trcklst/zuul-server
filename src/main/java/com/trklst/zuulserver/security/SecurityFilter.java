package com.trklst.zuulserver.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.trklst.zuulserver.feign.AuthenticationFeignService;
import com.trklst.zuulserver.security.config.SecurityProperties;
import com.trklst.zuulserver.security.excpetions.InvalidTokenException;
import com.trklst.zuulserver.security.excpetions.NoTokenSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class SecurityFilter extends ZuulFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private AuthenticationFeignService authenticationFeignService;
    private SecurityProperties securityProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        return securityProperties.getExcludedPatterns().stream()
                .noneMatch(pattern -> request.getRequestURI().startsWith(pattern));
    }

    @Override
    public Object run() throws ZuulException {
        String token = getToken();
        Map<String, Object> checkTokenResponse = callCheckToken(token);
        if (checkTokenResponse == null)
            throw new InvalidTokenException();
        boolean isValidToken = (boolean) checkTokenResponse.getOrDefault("active", false);
        if (isValidToken)
            return null;
        throw new InvalidTokenException();
    }

    private String getToken() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String authorization = request.getHeader("authorization");
        if (authorization == null)
            throw new NoTokenSendException();
        return authorization.replace(BEARER_PREFIX, "");
    }

    private Map<String, Object> callCheckToken(String token) {
        try {
            return authenticationFeignService.checkToken(token);
        } catch (Exception e) {
            return null;
        }
    }

    @Autowired
    public void setAuthenticationFeignService(AuthenticationFeignService authenticationFeignService) {
        this.authenticationFeignService = authenticationFeignService;
    }

    @Autowired
    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
