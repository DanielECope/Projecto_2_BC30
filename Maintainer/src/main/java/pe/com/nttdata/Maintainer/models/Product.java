package pe.com.nttdata.Maintainer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
	
	@Id
	private String id;
	@NotEmpty(message = "El campo name es requerido.")
	private String name;//cuenta corrienta// cuenta de ahorro
	@NotEmpty(message = "El campo productTypeId es requerido.")
	private String productTypeId;
	private ProductType productType;
	//private boolean multiplecuenta;//true
	
}
