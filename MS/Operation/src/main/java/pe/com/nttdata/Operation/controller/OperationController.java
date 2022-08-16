package pe.com.nttdata.Operation.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import pe.com.nttdata.Operation.model.Operation;
import pe.com.nttdata.Operation.service.IOperationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("api/1.0.0/operations")
public class OperationController {
	
	@Autowired
	private IOperationService service;
	
	@PostMapping
	public Mono<ResponseEntity<Mono<Operation>>> insert(@Valid @RequestBody Operation obj){
		Mono<Operation> operation = service.insert(obj);
		return Mono.just(new ResponseEntity<Mono<Operation>>(operation, HttpStatus.CREATED));
	}
	
	@GetMapping("/{identificationDocument}/{productId}")
	public Mono<ResponseEntity<Flux<Operation>>> findByIdentificationDocumentAndProductId(
			@PathVariable("identificationDocument") String identificationDocument,
			@PathVariable("productId") String productId){
		log.info("doc: "+identificationDocument);
		log.info("producto: "+productId);
		Flux<Operation> operations = service.findByIdentificationDocumentAndProductId(identificationDocument, productId);
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(operations));
	}
	
}
