package org.skillmatch.analizservice.clients.connectors;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillmatch.analizservice.clients.SciBoxClient;
import org.skillmatch.analizservice.config.SciBoxApi;
import org.skillmatch.analizservice.model.Message;
import org.skillmatch.analizservice.model.QwenRequest;
import org.skillmatch.analizservice.model.QwenResponse;
import org.skillmatch.analizservice.model.converter.QwenResponseConverter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SciBoxConnector {

    private final SciBoxClient sciBoxClient;

    private final SciBoxApi sciBoxApi;

    private final QwenResponseConverter converter;

    public Mono<List<QwenResponse>> chatCompletions(QwenRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, sciBoxApi.getApi());
        return sciBoxClient.chatCompletions(request.getModel(), request, headers)
                .doOnNext(r -> log.info("Result SciBoxClient chatCompletions={}", r))
                .flatMapMany(r -> {
                    List<JsonNode> nodes = new ArrayList<>();
                    Arrays.asList(r.get("choices").elements());
                    r.get("choices").elements().forEachRemaining(nodes::add);
                    return Flux.fromIterable(nodes);
                })
                .map(converter::toQwenResponse)
                .collectList();
    }
}
