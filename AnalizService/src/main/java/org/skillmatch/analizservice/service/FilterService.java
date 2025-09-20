package org.skillmatch.analizservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillmatch.analizservice.clients.connectors.SciBoxConnector;
import org.skillmatch.analizservice.model.AIParams;
import org.skillmatch.analizservice.model.FilterRequest;
import org.skillmatch.analizservice.model.Message;
import org.skillmatch.analizservice.model.QwenRequest;
import org.skillmatch.analizservice.model.QwenResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilterService {

    private final SciBoxConnector sciBoxConnector;

    public Mono<? extends Object> filter(FilterRequest filterRequest) {
        return (filterRequest.getAIRequest().isEmpty())
                ? aiFilter(filterRequest)
                : basicFilter(filterRequest);
    }

    private Mono<List<QwenResponse>> aiFilter(FilterRequest filterRequest) {
        return sciBoxConnector.chatCompletions(QwenRequest.builder()
                        .model("Qwen2.5-72B-Instruct-AWQ")
                        .messages(List.of(Message.builder()
                                .content(filterRequest.getAIRequest())
                                .role("user")
                                .build()))
                        .topP(AIParams.analyze.getTopP())
                        .frequency_penalty(AIParams.analyze.getFrequency_penalty())
                        .presence_penalty(AIParams.analyze.getPresence_penalty())
                        .maxTokens(AIParams.analyze.getMaxTokens())
                .build());
    }

    private Mono<Object> basicFilter(FilterRequest filterRequest) {
        return null; //TODO: дописать когда бд подключишь делать
    }
}
