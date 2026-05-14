package pe.edu.dfernandez.hexagonal.app.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Cuenta {
    private Long id;

    private Long cliente_id;

    private String numeroCuenta;

    private BigDecimal saldo;

    private String estado;

    private LocalDateTime fecha_creacion;

    private LocalDateTime fecha_actualizacion;
}
