package pe.com.nttdata.Operation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProduct {
	@Id
	private String id;
	@NotEmpty(message = "El campo customerId es requerido.")
	private String customerId;
	@NotEmpty(message = "El campo productId es requerido.")
	private String productId;
	@DecimalMin(value = "0.0", message = "El campo amountAvailable debe tener un valor mínimo de '0.0'.")
	@Digits(integer = 10, fraction = 3, message = "El campo amountAvailable tiene un formato no válido (#####.000).")
	@NotNull(message = "El campo amountAvailable es requerido.")
	private BigDecimal amountAvailable; // Monto disponible
	private BigDecimal creditLine; // Monto de la linea de crédito (Tarjeta de crédito)
	private LocalDateTime registerDate;
	//proyecto 2
	private int maxNumberTransactionsNoCommissions;
	private int numberOfMoves;
	private LocalDate paymentDate;

	private Customer customers;
	private Product product;
	
}
