package pe.com.nttdata.Contrato.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Contrato.model.CustomerProduct;
import reactor.core.publisher.Mono;

public interface ICustomerProductRepository extends ReactiveMongoRepository<CustomerProduct, String> {
    Mono<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId);
}
