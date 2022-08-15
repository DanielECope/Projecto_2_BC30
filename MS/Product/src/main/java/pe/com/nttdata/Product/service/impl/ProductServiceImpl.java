package pe.com.nttdata.Product.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.nttdata.Product.excepction.BadRequestException;
import pe.com.nttdata.Product.excepction.ModelNotFoundException;
import pe.com.nttdata.Product.models.Product;
import pe.com.nttdata.Product.repository.IProductRepository;
import pe.com.nttdata.Product.repository.IProductTypeRepository;
import pe.com.nttdata.Product.service.IProductService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements IProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private IProductRepository repo;
	
	@Autowired
	private IProductTypeRepository productTypeRepository;
	
	@Override
	public Mono<Product> insert(Product obj) {
		return productTypeRepository.findById(obj.getProductTypeId())
				.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo productTypeId tiene un valor no válido.")))
				.flatMap(productType -> {
					return repo.save(obj)
							.map(product -> {
								product.setProductType(productType);
								return product;
							});
				})
				.doOnNext(p -> logger.info("SE INSERTÓ EL PRODUCTO ::: " + p.getId()));
	}

	@Override
	public Mono<Product> update(Product obj) {
		if (obj.getId() == null || obj.getId().isEmpty())
			return Mono.error(() -> new BadRequestException("El campo id es requerido."));

		return repo.findById(obj.getId())
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("CLIENTE NO ENCONTRADO")))
				.flatMap(p -> {
					return productTypeRepository.findById(obj.getProductTypeId())
							.switchIfEmpty(Mono.error(() -> new BadRequestException("El campo productTypeId tiene un valor no válido.")))
							.flatMap(productType -> {
								return repo.save(obj)
										.map(product -> {
											product.setProductType(productType);
											return product;
										});
							});
				})
				.doOnNext(p -> logger.info("SE ACTUALIZÓ EL PRODUCTO ::: " + p.getId()));
	}

	@Override
	public Flux<Product> findAll() {
		return repo.findAll()
				.flatMap(product ->{
					return productTypeRepository.findById(product.getProductTypeId())
							.map(productType -> {
								product.setProductType(productType);
								return product;
							});
				});
	}

	@Override
	public Mono<Product> findById(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("PRODUCTO NO ENCONTRADO")))
				.flatMap(product ->{
					return productTypeRepository.findById(product.getProductTypeId())
							.map(productType -> {
								product.setProductType(productType);
								return product;
							});
				})
				.doOnNext(p -> logger.info("SE ENCONTRÓ EL PRODUCTO ::: " + id));
	}

	@Override
	public Mono<Void> delete(String id) {
		return repo.findById(id)
				.switchIfEmpty(Mono.error(() -> new ModelNotFoundException("PRODUCTO NO ENCONTRADO")))
				.flatMap(product -> repo.deleteById(product.getId()))
				.doOnNext(p -> logger.info("SE ELIMINÓ EL PRODUCTO ::: " + id));
	}

}
