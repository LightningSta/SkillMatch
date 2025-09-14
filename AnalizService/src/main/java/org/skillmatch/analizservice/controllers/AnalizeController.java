package org.skillmatch.analizservice.controllers;

import lombok.RequiredArgsConstructor;
import org.skillmatch.analizservice.service.AnalizeVacancyService;
import org.skillmatch.analizservice.utils.ReactiveResponseWrapper;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/analize/")
@RequiredArgsConstructor
public class AnalizeController {

    private final AnalizeVacancyService vacancyService;

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

}
