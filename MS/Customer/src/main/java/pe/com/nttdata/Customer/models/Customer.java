package pe.com.nttdata.Customer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {

	@Id
	private String id;
	@NotEmpty(message = "El campo fullName es requerido.")
	private String fullName;
	@NotEmpty(message = "El campo customerTypeId es requerido.")
	private String customerTypeId;
	private CustomerType customerType;
	@NotEmpty(message = "El campo identificationDocument es requerido.")
	private String identificationDocument;
	@NotEmpty(message = "El campo emailAddress es requerido.")
	@Email(message = "El campo emailAddress tiene un formato no válido.")
	private String emailAddress;
	
}
