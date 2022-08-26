package pe.com.nttdata.Contrato.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import pe.com.nttdata.Contrato.exception.BadRequestException;
import pe.com.nttdata.Contrato.exception.ModelNotFoundException;
import pe.com.nttdata.Contrato.model.Customer;
import pe.com.nttdata.Contrato.model.CustomerProduct;
import pe.com.nttdata.Contrato.model.Product;
import pe.com.nttdata.Contrato.repository.ICustomerProductRepository;
import pe.com.nttdata.Contrato.repository.ICustomerProductRepositoryMongo;
import pe.com.nttdata.Contrato.service.ICustomerProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
//feign client

@Service
public class CustomerProductServiceImpl implements ICustomerProductService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerProductServiceImpl.class);
	private final WebClient webClientCustomer= WebClient.create("http://localhost:7071/api/1.0.0/customers");
	//private final WebClient webClientCustomerType= WebClient.create("http://localhost:7071/api/1.0.0/customertypes");
	private final WebClient webClientProducts= WebClient.create("http://localhost:7072/api/1.0.0/products");

	
	@Autowired
	private ICustomerProductRepository repo;
	@Autowired
	private ICustomerProductRepositoryMongo repo2;
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	public CustomerProductServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Mono<Customer> findByIdCustomer(String id){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdCustomer ");
		return webClientCustomer.get().uri("/{id}",id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Customer.class);
	}
	public Customer findByIdCustomer2(String id){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdCustomer "+"http://localhost:7071/api/1.0.0/customers/"+id);
		Customer response = restTemplate.getForObject("http://localhost:7071/api/1.0.0/customers/"+id,Customer.class);
		return  response;
	}
	public Flux<Customer> countCustomer(String document){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdCustomer ");
		return webClientCustomer.get().uri("/accountByDocument/{document}",document)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(Customer.class);
	}

	public Mono<Product> findByIdProduct(String id){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdProduct ");
		return webClientProducts.get().uri("/{id}",id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Product.class);
	}
	public Product findByIdProduct2(String id){
		logger.info("Class: CustomerProductServiceImpl -> Method: findByIdProduct ");
		Product productResponse = restTemplate.getForObject("http://localhost:7072/api/1.0.0/products/"+id,Product.class);
		return  productResponse;
	}
	@Override
	public List<CustomerProduct> findByCustomerId(String customerId){
		return repo2.findByCustomerId(customerId);
	}

	@Override
	public CustomerProduct associateDebitCardAndBankAccount(String accountBankAccount,String accountDebitCard) {
		Mono<CustomerProduct> contractMono=this.findById(accountBankAccount).switchIfEmpty(Mono.error(() ->new BadRequestException("CUENTA NO ENCONTRADA ::: "+accountBankAccount)));
		Mono<CustomerProduct> creditCardMono=this.findById(accountDebitCard).switchIfEmpty(Mono.error(()->new BadRequestException("CUENTA NO ENCONTRADA ::: "+accountDebitCard)));
		CustomerProduct contract = null;
		if (contract.getProduct().getName()!="DEBIT")
		{
			logger.info("CUENTA INCORRECTA. SE DEBE ASOCIAR UNA TARJETA DE DEBITO ::: "+contract.getProduct().getName());
			//throw new RuntimeException("CUENTA INCORRECTA. SE DEBE ASOCIAR UNA TARJETA DE DEBITO ::: "+contract.getProduct().getName());
			return  null;
		}
		contract.setProductId(contractMono.block().getProductId());
		contract.setAmountAvailable(contractMono.block().getAmountAvailable());
		contract.setCustomerId(contractMono.block().getCustomerId());
		contract.setProductId(contractMono.block().getProductId());
		contract.setCustomers(contractMono.block().getCustomers());
		contract.setProduct(contractMono.block().getProduct());
		contract.setRegisterDate(contractMono.block().getRegisterDate());
		contract.setNumberOfMoves(contractMono.block().getNumberOfMoves());
		contract.setCreditLine(contractMono.block().getCreditLine());
		contract.setId(contractMono.block().getId());
		contract.setMaxNumberTransactionsNoCommissions(contractMono.block().getMaxNumberTransactionsNoCommissions());
		contract.setPaymentDate(contractMono.block().getPaymentDate());
		contract.setAssociateDebitCard(accountDebitCard);
		contract=repo2.save(contract);

		return  contract;
	}
	@Override
	public CustomerProduct insert(CustomerProduct obj) {
		logger.info("Class: CustomerProductServiceImpl -> Method: insert -> parameters:" + obj.toString());
		try {
			obj.setRegisterDate(LocalDateTime.now());
			Customer client = this.findByIdCustomer2(obj.getCustomerId());
			logger.info(client.toString());
			Product product = this.findByIdProduct2(obj.getProductId());
			logger.info(product.toString());
			List<CustomerProduct> contract = this.findByCustomerId(obj.getCustomerId())
					.stream()
					.filter(c->c.getProduct().getId().equals(product.getId())).toList();
			logger.info("Buscar cliente " + contract.toString());
			CustomerProduct finalObj = obj;
			List<CustomerProduct> existCredit= contract.stream().filter(c->c.getProduct().getProductType().getName().equals("CREDIT") && LocalDate.now().isAfter(c.getPaymentDate()) && c.getProductId().equals(finalObj.getProductId())).toList();
			if (existCredit.stream().count()>0){
				logger.info("CLIENTE TIENE UNA DEUDA DE ::: "+contract.get(0).getAmountAvailable());
				//throw new RuntimeException("CLIENTE TIENE UNA DEUDA DE ::: "+contract.get(0).getAmountAvailable());
				return  null;
			}


			if (contract.stream().count()>=1 && client.getCustomerType().getName().equals("PERSONAL") && ( product.getProductType().getName().equals("Bank account") || product.getProductType().getName().equals("CREDIT") )){
				logger.info("CLIENTE YA POSEE UNA CUENTA DEL TIPO "+product.getName().toUpperCase()+" ::: POR SER " + client.getCustomerType().getName());
				//throw new RuntimeException("cliente ya posee una cuenta"); CREDIT
				return  null;
			}else if (contract.stream().count()>=1 && client.getCustomerType().getName().equals("EMPRESARIAL") && product.getProductType().getName().equals("Bank account") && !( product.getName().equals("Cuenta corriente") ) ){
				logger.info("CLIENTE YA POSEE NO PUEDO TENER CUENTA DEL TIPO "+product.getName().toUpperCase()+" ::: POR SER " + client.getCustomerType().getName());
				//throw new RuntimeException("CLIENTE YA POSEE NO PUEDO TENER CUENTA DEL TIPO "+product.getName().toUpperCase()+" ::: POR SER " + client.getCustomerType().getName());
				return  null;
			}else if(product.getProductType().getName().equals("PERSONAL VIP") || product.getProductType().getName().equals("EMPRESARIAL PYME") ){
				//proyecto 2  Bank account
				List<CustomerProduct> contracts= this.findByCustomersIdAndProductId(obj.getCustomerId(),obj.getProductId()).stream().filter(cs->cs.getProduct().getProductType().getName().equals("Bank account")).toList();
				if (contracts.stream().count()==0){
					logger.info("CLIENTE DEBE POSEER UN CRÉDITO. => "+product.getName().toUpperCase()+" ::: " + client.getCustomerType().getName());
					//throw new RuntimeException("CLIENTE DEBE POSEER UN CRÉDITO. => "+product.getName().toUpperCase()+" ::: " + client.getCustomerType().getName());
					return  null;
				}
			}
			obj.setCustomers(client);
			obj.setProduct(product);
			obj.setNumberOfMoves(BigDecimal.valueOf(0));
			obj = repo2.save(obj);
			logger.info("SE INSERTÓ EL CONTRATO ::: " + obj.getId());

			return obj;
		}catch (Exception e){
			throw new BadRequestException("Error: "+e.getMessage());
		}

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
	public List<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId) {
		return  repo2.findByCustomersIdAndProductId(customersId, productId);
		/*
		return repo.findAll()
				.map(c->{
					logger.info("contratos: "+c.toString());
					return c;
				})
				.filter(contracts -> contracts.getCustomerId().equals(customersId) && contracts.getProductId().equals(productId))
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CONTRATO NO ENCONTRADO")))
				.next()
				.doOnNext(c -> logger.info("SE ENCONTRÓ EL CONTRATO DEL CLIENTE ::: " + customersId + " Y PRODUCTO ::: " + productId));

		 */
	}
}
