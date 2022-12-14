package pe.com.nttdata.Operation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "operations")
public class Operation {
	
	@Id
	private String id;
	@NotEmpty(message = "El campo customerProductId es requerido.")
	private String customerProductId;
	@NotEmpty(message = "El campo operationType es requerido.")
	private String operationType; // Depósito: D, Retiro: R, Pagos: P
	@DecimalMin(value = "0.0", message = "El campo amount debe tener un valor mínimo de '0.0'.")
	@Digits(integer = 10, fraction = 3, message = "El campo amount tiene un formato no válido (#####.000).")
	@NotNull(message = "El campo amount es requerido.")
	private BigDecimal amount;
	//proyecto 2
	private String 	originAccount;
	private String 	destinationAccount;
	private String operationCustomerId;

	private CustomerProduct customerProduct;
}
