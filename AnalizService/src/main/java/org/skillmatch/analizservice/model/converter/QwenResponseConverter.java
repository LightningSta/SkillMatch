package org.skillmatch.analizservice.model.converter;

import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Mapper;
import org.skillmatch.analizservice.model.Message;
import org.skillmatch.analizservice.model.QwenResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QwenResponseConverter {

    default QwenResponse toQwenResponse(JsonNode node) {
        return QwenResponse.builder()
                .index(node.get("index").asInt())
                .finishReason(node.get("finish_reason").asText())
                .message(Message.builder()
                        .content(node.get("message").get("content").asText())
                        .role(node.get("message").get("role").asText())
                        .build()
                ).build();
    }

    List<QwenResponse> toQwenResponseList(List<JsonNode> nodes);
}
