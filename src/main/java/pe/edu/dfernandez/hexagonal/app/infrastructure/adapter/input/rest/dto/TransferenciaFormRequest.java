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
public class TransferenciaFormRequest {

    private String cuentaOrigen;

    private String cuentaDestino;

    private BigDecimal monto;

    private BigDecimal comision;
}