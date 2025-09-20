package org.skillmatch.analizservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AnalizeEntity {

    private Long id;

    private byte[] file;

    private String result;

    private VacancyEntity vacancy;
}
