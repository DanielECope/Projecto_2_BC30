package pe.com.nttdata.Operation.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pe.com.nttdata.Operation.exception.ModelNotFoundException;
import pe.com.nttdata.Operation.model.Customer;
import pe.com.nttdata.Operation.model.CustomerProduct;
import pe.com.nttdata.Operation.model.Operation;
import pe.com.nttdata.Operation.model.Product;
import pe.com.nttdata.Operation.repository.IOperationRepository;
import pe.com.nttdata.Operation.repository.IOperationRepositoryMongo;
import pe.com.nttdata.Operation.service.IOperationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class OperationServiceImpl implements IOperationService {
	private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

	private final WebClient webClientCustomer= WebClient.create("http://localhost:7070/api/1.0.0/customers");
	private final WebClient webClientProducts= WebClient.create("http://localhost:7070/api/1.0.0/products");
	private final WebClient webClientContract= WebClient.create("http://localhost:7070/api/1.0.0/contracts");

	@Autowired
	private IOperationRepository repo;
	@Autowired
	private IOperationRepositoryMongo repo2;

	@Autowired
	RestTemplate restTemplate;

	public OperationServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Mono<CustomerProduct> findByIdContract(String id){
		logger.info("Class: OperationServiceImpl -> Method: findByIdContract ");
		return webClientContract.get().uri("/{id}",id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(CustomerProduct.class);
	}
	public CustomerProduct findByIdContract2(String id) {
		logger.info("Class: OperationServiceImpl -> Method: findByCustomersIdAndProductId2 ");
		logger.info("http://localhost:7073/api/1.0.0/contracts/"+id);
		CustomerProduct obj= restTemplate.getForObject("http://localhost:7073/api/1.0.0/contracts/"+id,CustomerProduct.class);
		logger.info(obj.toString());
		return obj;
	}
	public Product findByIdProduct2(String id){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdProduct ");
		return restTemplate.getForObject("http://localhost:7072/api/1.0.0/products/"+id,Product.class);
	}
	public Customer findByIdCustomer2(String id){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdCustomer "+"http://localhost:7071/api/1.0.0/customers/"+id);
		return restTemplate.getForObject("http://localhost:7071/api/1.0.0/customers/"+id,Customer.class);
	}

	public CustomerProduct updateContract2(CustomerProduct obj){
		logger.info("Class: OperationServiceImpl -> Method: updateContract2 ");
		restTemplate.put("http://localhost:7073/api/1.0.0/contracts/update",obj);
		return  obj;
	}
	public Mono<CustomerProduct> updateContract(CustomerProduct obj){
		logger.info("Class: OperationServiceImpl -> Method: updateContract ");
		return webClientContract.put().uri("/update",obj)
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(obj))
				.retrieve()
				.bodyToMono(CustomerProduct.class);
	}
	public CustomerProduct findByCustomersIdAndProductId2(String customersId, String productId) {
		logger.info("Class: OperationServiceImpl -> Method: findByCustomersIdAndProductId2 ");
		return restTemplate.getForObject("http://localhost:7070/api/1.0.0/contracts/findByCAndP?customersId="+customersId+"&productId="+productId,CustomerProduct.class);
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
	public Operation insert(Operation obj) {
		logger.info("Class: OperationServiceImpl -> Method: insert ");
		obj.setOperationType( obj.getOperationType().toUpperCase() );
		CustomerProduct contract;
		try{
			logger.info(findByIdContract(obj.getDestinationAccount()).toString());
			contract=findByIdContract2(obj.getDestinationAccount());
			Customer operationCustomer=this.findByIdCustomer2(obj.getOperationCustomerId());
			if (operationCustomer.getId().isEmpty()){
				logger.info("CLIENTE TIENE NO REGISTRADO ::: "+obj.getOperationCustomerId());
				//throw new RuntimeException("CLIENTE TIENE NO REGISTRADO ::: "+obj.getOperationCustomerId());
				return  null;
			}


		if(obj.getOperationType().equals("D"))//deposito
			contract.setAmountAvailable(contract.getAmountAvailable().add(obj.getAmount()));
		else if(obj.getOperationType().equals("R"))//retiro
			contract.setAmountAvailable(contract.getAmountAvailable().subtract(obj.getAmount()));
		else if(obj.getOperationType().equals("P") && obj.getCustomerProduct().getProduct().getName().equals("CREDIT"))//pago
			contract.setCreditLine(contract.getAmountAvailable().subtract(obj.getAmount()));


		BigDecimal commisionConvert = BigDecimal.valueOf(contract.getProduct().getCommission());
		if ( contract.getNumberOfMoves() >=contract.getMaxNumberTransactionsNoCommissions())
			contract.setAmountAvailable( contract.getAmountAvailable().add(commisionConvert) );

		contract.setNumberOfMoves(contract.getNumberOfMoves()+1);
		contract.setId(obj.getOriginAccount());//cambio de cuenta
		CustomerProduct contract2=this.updateContract2(contract);
		obj=repo2.save(obj);
		}catch (Exception e){
			logger.info(e.getMessage());
		}
		return obj;
		/*
				.flatMap(updateContract -> {
					return repo.save(obj)
							.map(operation -> {
								//logger.info("contrato: "+contract.toString());
								operation.setCustomerProduct(contract);
								return operation;
							});
				});
		return this.findByIdContract(obj.getCustomerProductId())
				.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerProductId tiene un valor no válido.")))
				.flatMap(contract -> {
					if(obj.getOperationType().equals("D"))
						contract.setAmountAvailable(contract.getAmountAvailable().add(obj.getAmount()));
					else if(obj.getOperationType().equals("R"))
						contract.setAmountAvailable(contract.getAmountAvailable().subtract(obj.getAmount()));

					if (contract.getNumberOfMoves()>=contract.getMaxNumberTransactionsNoCommissions())
						contract.setAmountAvailable( contract.getAmountAvailable().add(contract.getProduct().getCommission()) );

					contract.setNumberOfMoves(contract.getNumberOfMoves()+1);
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

		 */
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
