package pe.com.nttdata.Contrato.service;

import pe.com.nttdata.Contrato.model.CustomerProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, V> {
    Mono<T> insert(T obj);
    Mono<T> update(T obj);
    Flux<T> findAll();
    Mono<T> findById(V id);
    Mono<Void> delete(V id);
}
