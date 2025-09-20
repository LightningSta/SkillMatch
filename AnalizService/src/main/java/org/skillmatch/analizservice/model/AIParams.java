package org.skillmatch.analizservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AIParams {

    /**
     * Креативность / случайность
     */
    private double temperature;

    /**
     * Контроль разнообразия через ядро вероятностей
     */
    private double topP;

    /**
     * Максимальная длина ответа
     */
    private int maxTokens;

    private double frequency_penalty;

    private double presence_penalty;

    public static AIParams optimum = AIParams.builder()
            .topP(0.95)
            .maxTokens(260)
            .temperature(0.85)
            .frequency_penalty(0.1)
            .presence_penalty(0.1)
            .build();

    public static AIParams analyze = AIParams.builder()
            .topP(0.8)
            .maxTokens(200)
            .temperature(0.3)
            .frequency_penalty(0.0)
            .presence_penalty(0.0)
            .build();

    public static AIParams tallknig = AIParams.builder()
            .topP(0.9)
            .maxTokens(260)
            .temperature(0.7)
            .frequency_penalty(0.3)
            .presence_penalty(0.4)
            .build();
}
