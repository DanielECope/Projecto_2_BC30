package pe.com.nttdata.Contrato.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Contrato.model.CustomerProduct;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICustomerProductRepository extends ReactiveMongoRepository<CustomerProduct, String> {
    List<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId);
}
