package com.trklst.zuulserver.feign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("authentication")
public interface AuthenticationFeignService {

    @GetMapping("/oauth/check_token")
    @Headers("Authorization: Basic trcklst secret")
    Map<String, Object> checkToken(@RequestParam String token);
}
