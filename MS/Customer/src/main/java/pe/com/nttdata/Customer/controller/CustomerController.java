package pe.com.nttdata.Customer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.com.nttdata.Customer.models.Customer;
import pe.com.nttdata.Customer.service.ICustomerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("api/1.0.0/customers")
public class CustomerController {
	
	@Autowired
	private ICustomerService service;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Flux<Customer> findAll(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Mono<Customer> fintById(@PathVariable("id") String id){
		return service.findById(id);
	}

	@GetMapping("/accountByDocument/{document}")
	@ResponseStatus(code = HttpStatus.OK)
	public Flux<Customer> accountByDocument(@PathVariable("document") String document){
		return service.findByIdentificationDocument(document);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Customer> insert(@Valid @RequestBody Customer obj){
		return service.insert(obj);
	}
	
	@PutMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Customer> update(@Valid @RequestBody Customer obj){
		return service.update(obj);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable("id") String id) {
		return service.delete(id);
	}
}
