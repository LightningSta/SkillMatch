package org.skillmatch.analizservice.utils;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface ReactiveResponseWrapper<T> {

    Mono<T> produce();

    default Mono<ResponseEntity<T>> process(){
        return produce()
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().build());
    };
}
