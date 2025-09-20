package org.skillmatch.analizservice.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.skillmatch.analizservice.service.SkillExtractionService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@RestController
@RequestMapping("/api/analize/v1/document/")
@RequiredArgsConstructor
public class DocumentController {

    private final SkillExtractionService skillExtractionService;

    @PostMapping("/extract")
    public Mono<ResponseEntity<String>> extract(@RequestPart("file") Mono<Resource> filePart) {
        try {
            return filePart
                    .map(skillExtractionService::extractSkills)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.badRequest().build());
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
    }

    @PostMapping("/extracts")
    public Mono<ResponseEntity<String>> extract(@RequestPart("files") Flux<Resource> files) {
        try {
            return files
                    .map(file -> {
                        return Tuples.of(file.getFilename(), skillExtractionService.extractSkills(file));
                    })
                    .reduce(new JSONObject(), (pair, result) -> {
                        return pair.append(result.getT1(), new JSONObject(result.getT2()));
                    })
                    .map(jsonObject -> jsonObject.toString(2))
                    .map(ResponseEntity::ok);
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
    }

}
