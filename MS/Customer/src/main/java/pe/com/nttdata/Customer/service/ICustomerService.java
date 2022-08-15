package pe.com.nttdata.Customer.service;


import pe.com.nttdata.Customer.models.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService extends ICRUD<Customer, String> {
	Mono<Boolean> existCustomer(String identificationDocument);
	public Flux<Customer> findByIdentificationDocument(String document);
}
