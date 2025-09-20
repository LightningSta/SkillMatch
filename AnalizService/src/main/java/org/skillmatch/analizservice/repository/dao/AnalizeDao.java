package org.skillmatch.analizservice.repository.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillmatch.analizservice.model.AnalizeEntity;
import org.skillmatch.analizservice.repository.mapper.AnalizeMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalizeDao {

    private final AnalizeMapper analizyMapper;

    public Mono<AnalizeEntity> selectAnalizeById(Long id) {
        return Mono.fromCallable(() -> analizyMapper.selectAnalizeById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(a -> log.info("Analize found: {}", a));
    }

    public Mono<List<AnalizeEntity>> selectAllAnalizes() {
        return Mono.fromCallable(analizyMapper::selectAllAnalizes)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(list -> log.info("Loaded {} analizes", list.size()));
    }

    public Mono<AnalizeEntity> insertAnalize(AnalizeEntity analize) {
        return Mono.fromCallable(() -> {
                    analizyMapper.insertAnalize(analize);
                    return analize;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(a -> log.info("Analize inserted: {}", a));
    }

    public Mono<Void> updateAnalize(AnalizeEntity analize) {
        return Mono.fromRunnable(() -> analizyMapper.updateAnalize(analize))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(x -> log.info("Analize updated: {}", analize))
                .then();
    }

    public Mono<Void> deleteAnalize(Long id) {
        return Mono.fromRunnable(() -> analizyMapper.deleteAnalize(id))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(x -> log.info("Analize deleted: {}", id))
                .then();
    }
}
