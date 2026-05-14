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
public class TransaccionRequest {
        
    private Long cuenta_origen_id;    

    private Long cuenta_destino_id;
    
    private BigDecimal monto;
    
    private String tipo; // "DEPOSITO" o "RETIRO"

    private String descripcion;
    
    private String estado;
}
