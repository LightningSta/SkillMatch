package org.skillmatch.analizservice.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class AnalizeVacancyService {
    private final ChatModel chatModel;

    private final SkillExtractionService skillExtractionService;
    private final ParserVacancy parserVacancy;

    protected String analizyVacancy(String skills, String vacancy) {
        String prompt = """
                Твоя задача оценить на сколько по навыкам данный кандидат подходит на данную вакансию
                Ответ дай в процентах
                Резюме:
                """+ vacancy+"""
                
                Навыки кандидата:
                """ + skills;
        ChatResponse response = chatModel.call(new Prompt(prompt));
        return response.getResult().getOutput().getText();
    }

    @Cacheable("vacancy")
    public String analizVacancy(String skills, String vacancy) {
        return analizyVacancy(skills,vacancy);
    }

    public Mono<String> extract(Flux<Resource> files, String vacancy_URL){
        return files
                .map(file -> Tuples.of(file.getFilename(), skillExtractionService.extractSkills(file)))
                .map(pair -> Tuples.of(pair.getT1(), analizVacancy(pair.getT2(), parserVacancy.extractVacancy(vacancy_URL))))
                .reduce(new JSONObject(), (pair, result) -> pair.append(result.getT1(), result.getT2()))
                .map(jsonObject -> jsonObject.toString(2));
    }
}
