package pe.com.nttdata.Contrato.service;

import pe.com.nttdata.Contrato.model.CustomerProduct;
import reactor.core.publisher.Mono;

public interface ICustomerProductService extends ICRUD<CustomerProduct, String> {
	Mono<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId);
}
