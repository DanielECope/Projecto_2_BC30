package pe.com.nttdata.Operation.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.nttdata.Operation.model.Operation;

public interface IOperationRepository extends ReactiveMongoRepository<Operation, String> {}
