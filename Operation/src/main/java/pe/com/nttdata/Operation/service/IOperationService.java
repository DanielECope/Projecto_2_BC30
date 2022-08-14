package pe.com.nttdata.Operation.service;


import pe.com.nttdata.Operation.model.Operation;
import reactor.core.publisher.Flux;

public interface IOperationService extends ICRUD<Operation, String> {
	Flux<Operation> findByIdentificationDocumentAndProductId(String identificationDocument, String productId);
}
