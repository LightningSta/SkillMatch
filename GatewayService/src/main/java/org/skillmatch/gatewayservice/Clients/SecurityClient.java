package org.skillmatch.gatewayservice.Clients;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(name = "SecurityService", url = "http://localhost:8071")
public interface SecurityClient {

    @GetMapping("/api/security/validate")
    Mono<String> validate(@RequestParam String token);
}
