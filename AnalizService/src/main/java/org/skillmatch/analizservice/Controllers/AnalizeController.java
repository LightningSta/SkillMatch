package org.skillmatch.analizservice.Controllers;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.skillmatch.analizservice.Service.AnalizeVacancyService;
import org.skillmatch.analizservice.Service.ParserVacancy;
import org.skillmatch.analizservice.Service.SkillExtractionService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@RestController
@RequestMapping("/api/analize/")
@RequiredArgsConstructor
public class AnalizeController {

    private final SkillExtractionService skillExtractionService;
    private final AnalizeVacancyService vacancyService;
    private final ParserVacancy parserVacancy;

    @PostMapping("/vacancy")
    public Mono<ResponseEntity<String>> extract(@RequestPart("files") Flux<Resource> files, @RequestPart("vacancy") String vacancy_URL) {
        return files
                .map(file->Tuples.of(file.getFilename(), skillExtractionService.extractSkills(file)))
                .map(pair->Tuples.of(pair.getT1(), vacancyService.analizVacancy(pair.getT2(), parserVacancy.extractVacancy(vacancy_URL))))
                .reduce(new JSONObject(),(pair, result)->pair.append(result.getT1(),result.getT2()))
                .map(jsonObject -> jsonObject.toString(2))
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.internalServerError().body("Service error"));
    }

}
