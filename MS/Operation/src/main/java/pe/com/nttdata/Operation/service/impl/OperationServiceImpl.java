package pe.com.nttdata.Operation.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pe.com.nttdata.Operation.exception.BadRequestException;
import pe.com.nttdata.Operation.exception.ModelNotFoundException;
import pe.com.nttdata.Operation.model.Customer;
import pe.com.nttdata.Operation.model.CustomerProduct;
import pe.com.nttdata.Operation.model.Operation;
import pe.com.nttdata.Operation.model.Product;
import pe.com.nttdata.Operation.repository.IOperationRepository;
import pe.com.nttdata.Operation.service.IOperationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OperationServiceImpl implements IOperationService {
	private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

	private final WebClient webClientCustomer= WebClient.create("http://localhost:8081/api/1.0.0/customers");
	private final WebClient webClientProducts= WebClient.create("http://localhost:8084/api/1.0.0/products");
	private final WebClient webClientContract= WebClient.create("http://localhost:8082/api/1.0.0/contracts");

	@Autowired
	private IOperationRepository repo;

	public Mono<CustomerProduct> findByIdContract(String id){
		logger.info("Class: OperationServiceImpl -> Method: findByIdContract ");
		return webClientContract.get().uri("/{id}",id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(CustomerProduct.class);
	}

	public Mono<CustomerProduct> updateContract(CustomerProduct obj){
		logger.info("Class: OperationServiceImpl -> Method: updateContract ");
		return webClientContract.put().uri("/update",obj)
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(obj))
				.retrieve()
				.bodyToMono(CustomerProduct.class);
	}

	public Mono<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId){
		logger.info("Class: OperationServiceImpl -> Method: findByCustomersIdAndProductId ");
		return webClientContract.get().uri("/findByCAndP?customersId="+customersId+"&productId="+productId)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(CustomerProduct.class);
	}

	public Flux<Customer> findAllCustomer(){
		logger.info("Class: OperationServiceImpl -> Method: findAllCustomer ");
		return webClientCustomer.get().uri("")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(Customer.class);
	}

	public Mono<Product> findByIdProduct(String id){
		logger.info("Class: OperationServiceImpl -> Method: findByIdProduct ");
		return webClientProducts.get().uri("/{id}",id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Product.class);
	}

	@Override
	public Mono<Operation> insert(Operation obj) {
		logger.info("Class: OperationServiceImpl -> Method: insert ");
		obj.setOperationType( obj.getOperationType().toUpperCase() );

		return this.findByIdContract(obj.getCustomerProductId())
				.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerProductId tiene un valor no válido.")))
				.flatMap(contract -> {
					if(obj.getOperationType().equals("D"))
						contract.setAmountAvailable(contract.getAmountAvailable().add(obj.getAmount()));
					else if(obj.getOperationType().equals("R"))
						contract.setAmountAvailable(contract.getAmountAvailable().subtract(obj.getAmount()));
//					else if(obj.getOperationType().equals("P"))
//						contract.setAmountAvailable(contract.getAmountAvailable().add(obj.getAmount()));
					
					return this.updateContract(contract)
							.flatMap(updateContract -> {
								return repo.save(obj)
										.map(operation -> {
											logger.info("contrato: "+contract.toString());
											operation.setCustomerProduct(contract);
											return operation;
										});
							});
				})
				.doOnNext(o -> logger.info("SE INSERTÓ EL MOVIMIENTO ::: " + o.getId()));
	}

	@Override
	public Mono<Operation> update(Operation obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Operation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Operation> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Operation> findByIdentificationDocumentAndProductId(String identificationDocument, String productId) {
		return this.findAllCustomer()
				.filter(customers -> customers.getIdentificationDocument().equals(identificationDocument))
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CLIENTE NO ENCONTRADO")))
				.next()
				.flatMapMany(customer -> {
					return this.findByIdProduct(productId)
							.flatMapMany(product -> {
								return this.findByCustomersIdAndProductId(customer.getId(), productId)
										.flatMapMany(contract -> {
											return repo.findAll()
													.filter(operations -> operations.getCustomerProductId().equals(contract.getId()))
													.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("MOVIMIENTO NO ENCONTRADO")))
													.map(o -> {
														contract.setCustomers(customer);
														contract.setProduct(product);
														o.setCustomerProduct(contract);
														return o;
													})
													.doOnNext(o -> logger.info("SE ENCONTRÓ LAS MOVIMIENTOS DEL CLIENTE ::: " + customer.getFullName()));
										});
							});
				});
	}

}
