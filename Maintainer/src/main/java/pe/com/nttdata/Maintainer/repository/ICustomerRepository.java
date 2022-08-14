package pe.com.nttdata.Maintainer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Maintainer.models.Customer;
import reactor.core.publisher.Flux;

public interface ICustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Flux<Customer> findByIdentificationDocument(String document);
}
