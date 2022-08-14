package pe.com.nttdata.Maintainer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Maintainer.models.Product;

public interface IProductRepository extends ReactiveMongoRepository<Product, String> {}
