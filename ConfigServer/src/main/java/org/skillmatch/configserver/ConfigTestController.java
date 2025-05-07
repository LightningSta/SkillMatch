package org.skillmatch.configserver;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ConfigTestController {

    @GetMapping(value = "/{name}", produces = "text/plain")
    public ResponseEntity<Resource> getConfigFile(@PathVariable String name) throws IOException {
        Path file = Paths.get("./configurations/" + name);
        if (Files.exists(file)) {
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
