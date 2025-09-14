package org.skillmatch.analizservice.repository.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillmatch.analizservice.model.Vacancy;
import org.skillmatch.analizservice.repository.mapper.AnalizyMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalizyDao {

    private final AnalizyMapper analizyMapper;

    public Mono<Vacancy> getVacancyById(Integer id) {
        return Mono.fromCallable(() -> analizyMapper.getVacancyById(id))
                .doOnNext(r -> log.info("Vacancy found: {}", r));
    }
}
