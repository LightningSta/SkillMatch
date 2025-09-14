package org.skillmatch.analizservice.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.skillmatch.analizservice.model.Vacancy;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParserVacancy {

    public String extractVacancy(String url) {
        try {
            StringBuilder vacancy_description= new StringBuilder();
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            Elements vacancies = document.select("[data-qa=vacancy-description]");
            for (Element vacancy : vacancies) {
                vacancy_description.append(vacancy.text());
            }
            return Vacancy.builder()
                    .vacancyName(document.title())
                    .vacancyDescription(vacancy_description.toString())
                    .vacancySkills(extractBasic(url))
                    .vacancyUrl(url)
                    .build().toString();

        } catch (IOException e) {
            return "Ошибка при загрузке страницы";
        }

    }

    protected String extractBasic(String url){
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            Elements vacancies = document.select("li[data-qa=skills-element]");
            StringBuilder vacancy_skills= new StringBuilder("Ключевые навыки:");
            for (Element vacancy : vacancies) {
                vacancy_skills=vacancy_skills.append(vacancy.selectFirst("div div").text()+",");
            }
            return vacancy_skills.toString();
        } catch (IOException e) {
            return "Ошибка при загрузке страницы";
        }
    }
}
