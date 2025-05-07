package org.skillmatch.analizservice.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalizeVacancyService {
    private final ChatModel chatModel;


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
}
