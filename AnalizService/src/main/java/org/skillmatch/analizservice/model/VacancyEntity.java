package org.skillmatch.analizservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class VacancyEntity {
    private Long id;

    private String name;

    private String description;

    private UUID userId;
}
