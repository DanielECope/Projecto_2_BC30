package pe.com.nttdata.Contrato.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.com.nttdata.Contrato.exception.BadRequestException;
import pe.com.nttdata.Contrato.exception.ModelNotFoundException;
import pe.com.nttdata.Contrato.model.Customer;
import pe.com.nttdata.Contrato.model.CustomerProduct;
import pe.com.nttdata.Contrato.model.Product;
import pe.com.nttdata.Contrato.repository.ICustomerProductRepository;
import pe.com.nttdata.Contrato.service.ICustomerProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
//feign client

@Service
public class CustomerProductServiceImpl implements ICustomerProductService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerProductServiceImpl.class);
	private final WebClient webClientCustomer= WebClient.create("http://localhost:8081/api/1.0.0/customers");
	private final WebClient webClientCustomerType= WebClient.create("http://localhost:8081/api/1.0.0/customertypes");
	private final WebClient webClientProducts= WebClient.create("http://localhost:8081/api/1.0.0/products");

	
	@Autowired
	private ICustomerProductRepository repo;


	public Mono<Customer> findByIdCustomer(String id){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdCustomer ");
		return webClientCustomer.get().uri("/{id}",id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Customer.class);
	}
	public Mono<String> countCustomer(String document){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdCustomer ");
		return webClientCustomer.get().uri("/countAccountByDocument/{document}",document)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(String.class);
	}

	public Mono<Product> findByIdProduct(String id){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdProduct ");
		return webClientProducts.get().uri("/{id}",id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Product.class);
	}
	
	int cantidad=0;
	@Override
	public Mono<CustomerProduct> insert(CustomerProduct obj) {
		logger.info("Class: CustomerProductServiceImpl -> Method: insert -> parameters:" + obj.toString());
		obj.setRegisterDate(LocalDateTime.now());

		return this.findByIdCustomer(obj.getCustomerId())
				.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerId tiene un valor no válido.")))
				.flatMap(customer ->{
						logger.info("cantidad: "+this.countCustomer(customer.getIdentificationDocument()));
					return this.findByIdProduct(obj.getProductId())
							.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo productId tiene un valor no válido.")))
							.flatMap(product -> {
								return repo.save(obj)
										.map(contract -> {
											contract.setCustomers(customer);
											contract.setProduct(product);
											return contract;
										});
							});
				})
				.doOnNext(c -> logger.info("SE INSERTÓ EL CONTRATO ::: " + c.getId()));
	}

	@Override
	public Mono<CustomerProduct> update(CustomerProduct obj) {

		if (obj.getId() == null || obj.getId().isEmpty())
			return Mono.error(() -> new BadRequestException("El campo id es requerido."));
		
		return repo.findById(obj.getId())
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.flatMap(c -> {
					return this.findByIdCustomer(obj.getCustomerId())
							.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo customerId tiene un valor no válido.")))
							.flatMap(customer ->{
								return this.findByIdProduct(obj.getProductId())
										.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo productId tiene un valor no válido.")))
										.flatMap(product ->{
											return repo.save(obj)
													.map(contract -> {
														contract.setCustomers(customer);
														contract.setProduct(product);
														return contract;
													});
										});
							});
				})
				.doOnNext(c -> logger.info("SE ACTUALIZÓ EL CONTRATO ::: " + c.getId()));


	}

	@Override
	public Flux<CustomerProduct> findAll() {
		return repo.findAll()
				.flatMap(contract -> {
					return this.findByIdCustomer(contract.getCustomerId())
							.flatMap(customer -> {
								return this.findByIdProduct(contract.getProductId())
										.map(product -> {
											contract.setCustomers(customer);
											contract.setProduct(product);
											return contract;
										});
							});
				});


	}

	@Override
	public Mono<CustomerProduct> findById(String id) {

		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.flatMap(contract ->{
					return this.findByIdCustomer(contract.getCustomerId())
							.flatMap(customer -> {
								return this.findByIdProduct(contract.getProductId())
										.map(product -> {
											contract.setCustomers(customer);
											contract.setProduct(product);
											return contract;
										});
							});
				})
				.doOnNext(c -> logger.info("SE ENCONTRÓ EL CONTRATO ::: " + id));
	}

	@Override
	public Mono<Void> delete(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.flatMap(contract -> repo.deleteById(contract.getId()))
				.doOnNext(c -> logger.info("SE ELIMINÓ EL CONTRATO ::: " + id));

	}

	@Override
	public Mono<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId) {
		return repo.findAll()
				.map(c->{
					logger.info("contratos: "+c.toString());
					return c;
				})
				.filter(contracts -> contracts.getCustomerId().equals(customersId) && contracts.getProductId().equals(productId))
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.next()
				.doOnNext(c -> logger.info("SE ENCONTRÓ EL CONTRATO DEL CLIENTE ::: " + customersId + " Y PRODUCTO ::: " + productId));
	}
}
