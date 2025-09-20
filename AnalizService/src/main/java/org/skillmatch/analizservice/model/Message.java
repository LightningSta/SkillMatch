package org.skillmatch.analizservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    /**
     * Содержимое ответа
     */
    private String content;

    /**
     * Роль при ответе
     */
    private String role;

}
