package org.skillmatch.analizservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "client.sci-box")
public class SciBoxApi {

    private String api;

}
