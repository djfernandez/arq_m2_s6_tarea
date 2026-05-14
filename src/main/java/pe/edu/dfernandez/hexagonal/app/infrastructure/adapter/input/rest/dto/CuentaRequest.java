package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaRequest {
    
    private Long cliente_id;

    private String numeroCuenta;

    private BigDecimal saldo;

    private String estado;
}
