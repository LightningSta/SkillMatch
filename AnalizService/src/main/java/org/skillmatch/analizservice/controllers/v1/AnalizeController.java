package org.skillmatch.analizservice.controllers.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillmatch.analizservice.model.FilterRequest;
import org.skillmatch.analizservice.service.AnalizeVacancyService;
import org.skillmatch.analizservice.service.FilterService;
import org.skillmatch.analizservice.utils.ReactiveResponseWrapper;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/analize/v1/analize")
@RequiredArgsConstructor
@Slf4j
public class AnalizeController {

    private final AnalizeVacancyService vacancyService;

    private final FilterService filterService;

    @PostMapping("/vacancy")
    public Mono<ResponseEntity<String>> extract(@RequestPart("files") Flux<Resource> files, @RequestPart("vacancy") String vacancy_URL) {
        return new ReactiveResponseWrapper<String> (
        ) {
            @Override
            public Mono<String> produce() {
                return vacancyService.extract(files, vacancy_URL);
            }
        }.process();
    }

    @PostMapping("/filter")
    public Mono<ResponseEntity<Object>> filter(@RequestBody FilterRequest request) {
        return new ReactiveResponseWrapper<Object> (
        ) {
            @Override
            public Mono<Object> produce() {
                return (Mono<Object>) filterService.filter(request);
            }
        }.process();
    }
}
