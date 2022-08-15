package pe.com.nttdata.Customer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Customer.models.Customer;
import reactor.core.publisher.Flux;

public interface ICustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Flux<Customer> findByIdentificationDocument(String document);
}
