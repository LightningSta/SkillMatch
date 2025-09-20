package org.skillmatch.analizservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactivefeign.retry.BasicReactiveRetryPolicy;
import reactivefeign.retry.ReactiveRetryPolicy;

@Configuration
public class ReactiveFeignConfig {

    @Bean
    public ReactiveRetryPolicy retryPolicy() {
        return BasicReactiveRetryPolicy.retryWithBackoff(3, 500l);
    }

}
