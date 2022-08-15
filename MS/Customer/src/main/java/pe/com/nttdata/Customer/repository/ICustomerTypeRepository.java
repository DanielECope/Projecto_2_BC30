package pe.com.nttdata.Customer.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Customer.models.CustomerType;

public interface ICustomerTypeRepository extends ReactiveMongoRepository<CustomerType, String> {}
