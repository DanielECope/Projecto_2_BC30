package pe.com.nttdata.Contrato.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICRUD<T, V> {
    Mono<T> update(T obj);
    Flux<T> findAll();
    Mono<T> findById(V id);
    Mono<Void> delete(V id);
}
