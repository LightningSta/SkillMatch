package org.skillmatch.analizservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillExtractionService {

    private final ChatModel chatModel;
    private final DocumentReaderService documentReaderService;

    public String extractSkills(String text) {
        String prompt = """
                Раздели перечисленные навыки на две группы: Hard Skills (технические) и Soft Skills (личные).
                (Если есть возможность ТОЛЬКО Soft Skills представлять НА РУССКОМ ЯЗЫКЕ)
                Ответ в JSON:
                {
                  "hard_skills": [...],
                  "soft_skills": [...]
                }

                Текст:
                """ + text;
        ChatResponse response = chatModel.call(new Prompt(prompt));
        return response.getResult().getOutput().getText();
    }

    @Cacheable(value = "skills" , key = "#resource.filename")
    public String extractSkills(Resource resource) {
        return extractSkills(documentReaderService.loadText(resource).toString());
    }

}
