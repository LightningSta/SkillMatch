package org.skillmatch.analizservice.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class FilterRequest {

    private String AIRequest;

    private JsonNode basicFilter;
}
