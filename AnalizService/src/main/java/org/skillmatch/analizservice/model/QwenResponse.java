package org.skillmatch.analizservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QwenResponse {

    /**
     * Причина завершения
     */
    private String finishReason;

    private Integer index;

    /**
     * Ответ ИИ
     */
    private Message message;
}
