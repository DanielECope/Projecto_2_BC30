package pe.com.nttdata.Maintainer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Maintainer.models.ProductType;

public interface IProductTypeRepository extends ReactiveMongoRepository<ProductType, String> {}
