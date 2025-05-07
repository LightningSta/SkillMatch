package org.skillmatch.gatewayservice.Filters;

import org.skillmatch.gatewayservice.Clients.SecurityClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class ValidateFilter extends AbstractGatewayFilterFactory<ValidateFilter.Config> {

    public ValidateFilter() {
        super(Config.class);
    }

    @Autowired
    @Lazy
    private SecurityClient securityClient;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            return Mono.just(exchange.getRequest().getURI().getPath())
                    .flatMap(path-> {
                        if(path.contains("/login")) {
                            return chain.filter(exchange);
                        }else{
                            return Mono.defer(()->securityClient.validate(String.valueOf(exchange.getRequest().getHeaders().get("Authorization"))))
                                    .onErrorResume(error->{
                                        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
                                        return Mono.empty();
                                    })
                                    .flatMap(responseM ->{
                                        return chain.filter(exchange);
                                    });
                        }
                    });
        });
    }

    public static class Config {
    }
}
