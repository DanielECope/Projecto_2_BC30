package pe.com.nttdata.Operation.service;

import pe.com.nttdata.Operation.model.Operation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, V> {

	Operation insert(T obj);
	Mono<T> update(T obj);
	Flux<T> findAll();
	Mono<T> findById(V id);
	Mono<Void> delete(V id);
}
