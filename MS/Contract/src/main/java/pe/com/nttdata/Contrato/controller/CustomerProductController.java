package pe.com.nttdata.Contrato.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.com.nttdata.Contrato.model.Customer;
import pe.com.nttdata.Contrato.model.CustomerProduct;
import pe.com.nttdata.Contrato.model.Product;
import pe.com.nttdata.Contrato.service.ICustomerProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/1.0.0/contracts")
public class CustomerProductController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerProductController.class);
	
	@Autowired
	private ICustomerProductService service;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Flux<CustomerProduct> findAll(){
		logger.info("Inicio ::: findAll");
		return service.findAll()
				.doOnNext(x -> logger.info("Fin ::: findAll"));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Mono<CustomerProduct> fintById(@PathVariable("id") String id){
		return service.findById(id);
	}

	@GetMapping("/findByCAndP")
	public List<CustomerProduct> findByCustomersIdAndProductId(String customersId, String productId) {
		logger.info("cliente: "+customersId);
		logger.info("producto:"+productId);
		return service.findByCustomersIdAndProductId(customersId,productId);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerProduct insert(@Valid @RequestBody CustomerProduct obj){
		return service.insert(obj);
	}
	
	@PutMapping("/update")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<CustomerProduct> update(@Valid @RequestBody CustomerProduct obj){
		return service.update(obj);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable("id") String id) {
		return service.delete(id);
	}

}
