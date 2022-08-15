package pe.com.nttdata.Product.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Product.models.Product;

public interface IProductRepository extends ReactiveMongoRepository<Product, String> {}
