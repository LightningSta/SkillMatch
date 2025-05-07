package org.skillmatch.analizservice.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vacancy {

    private String vacancyName;

    private String vacancyDescription;

    private String vacancyUrl;

    private String vacancySkills;

    @Override
    public String toString() {
        return vacancyName+"\n"+vacancyDescription+"\n"+vacancySkills;
    }
}
