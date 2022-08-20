package pe.com.nttdata.Operation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pe.com.nttdata.Operation.model.Operation;

public interface IOperationRepositoryMongo extends MongoRepository<Operation,String> {
}
