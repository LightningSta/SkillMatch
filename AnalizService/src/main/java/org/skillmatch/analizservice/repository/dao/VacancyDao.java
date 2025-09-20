package org.skillmatch.analizservice.repository.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillmatch.analizservice.model.VacancyEntity;
import org.skillmatch.analizservice.repository.mapper.VacancyMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacancyDao {

    private final VacancyMapper vacancyMapper;

    public Mono<VacancyEntity> selectVacancyById(Long id) {
        return Mono.fromCallable(() -> vacancyMapper.selectVacancyById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(v -> log.info("Vacancy found: {}", v));
    }

    public Mono<List<VacancyEntity>> selectAllVacancies() {
        return Mono.fromCallable(vacancyMapper::selectAllVacancies)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(list -> log.info("Loaded {} vacancies", list.size()));
    }

    public Mono<VacancyEntity> insertVacancy(VacancyEntity vacancy) {
        return Mono.fromCallable(() -> {
                    vacancyMapper.insertVacancy(vacancy);
                    return vacancy;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(v -> log.info("Vacancy inserted: {}", v));
    }

    public Mono<Void> updateVacancy(VacancyEntity vacancy) {
        return Mono.fromRunnable(() -> vacancyMapper.updateVacancy(vacancy))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(x -> log.info("Vacancy updated: {}", vacancy))
                .then();
    }

    public Mono<Void> deleteVacancy(Long id) {
        return Mono.fromRunnable(() -> vacancyMapper.deleteVacancy(id))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(x -> log.info("Vacancy deleted: {}", id))
                .then();
    }
}
