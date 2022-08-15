package pe.com.nttdata.Product.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Product.models.ProductType;

public interface IProductTypeRepository extends ReactiveMongoRepository<ProductType, String> {}
