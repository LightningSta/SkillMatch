package org.skillmatch.gatewayservice;

import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class PathsFree {

    public static List<String> paths = List.of("/api/security/login"
            ,"/api/security/registry"
            ,"/api/security/check"
    );

}
