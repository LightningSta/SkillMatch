package org.skillmatch.analizservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class QwenRequest extends AIParams {

    private String model;

    private List<Message> messages;

    @Override
    public String toString() {
        try {
            return new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return super.toString();
        }
    }

}
