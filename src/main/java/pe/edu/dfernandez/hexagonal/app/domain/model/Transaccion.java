package pe.edu.dfernandez.hexagonal.app.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Transaccion {
    
    private Long id;
    
    private Long cuenta_origen_id;    

    private Long cuenta_destino_id;
    
    private BigDecimal monto;
    
    private String tipo; // "DEPOSITO" o "RETIRO"

    private String descripcion;
    
    private String estado;
    
    private LocalDateTime fecha_creacion;
    
    private LocalDateTime fecha_actualizacion;
}
