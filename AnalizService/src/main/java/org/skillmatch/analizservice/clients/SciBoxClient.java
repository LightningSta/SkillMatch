package org.skillmatch.analizservice.clients;

import com.fasterxml.jackson.databind.JsonNode;
import org.skillmatch.analizservice.config.ReactiveFeignConfig;
import org.skillmatch.analizservice.model.QwenRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(name = "SciBoxService", url = "${client.sci-box.url}", configuration = ReactiveFeignConfig.class)
public interface SciBoxClient {

    @PostMapping("/engines/{model}/chat/completions")
    Mono<JsonNode> chatCompletions(@PathVariable("model") String model,
                                   @RequestBody QwenRequest request,
                                   @RequestHeader HttpHeaders headers);
}
