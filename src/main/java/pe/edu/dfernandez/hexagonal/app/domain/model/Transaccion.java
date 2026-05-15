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

    private BigDecimal comision;
    
    private String tipo; // "DEPOSITO" o "RETIRO"

    private String descripcion;
    
    private String estado;
    
    private LocalDateTime fecha_creacion;
    
    private LocalDateTime fecha_actualizacion;


    public void validateTransaccionInput() {
        if (!hasValidCuentaOrigenId()) {
            throw new IllegalArgumentException("El ID de la cuenta de origen es inválido");
        }

        if (!hasValidCuentaDestinoId()) {
            throw new IllegalArgumentException("El ID de la cuenta de destino es inválido");
        }

        if (!hasValidMonto()) {
            throw new IllegalArgumentException("El monto es inválido");
        }

        if (!hasValidComision()) {
            throw new IllegalArgumentException("La comisión es inválida");
        }

        if (!hasValidTipo()) {
            throw new IllegalArgumentException("El tipo de transacción es inválido");
        }
    }


    public boolean hasValidCuentaOrigenId() {
        return cuenta_origen_id != null && cuenta_origen_id > 0;
    }

    public boolean hasValidCuentaDestinoId() {
        return cuenta_destino_id != null && cuenta_destino_id > 0;
    }

    public boolean hasValidMonto() {
        return monto != null && monto.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasValidComision() {
        return comision != null && comision.compareTo(BigDecimal.ZERO) >= 0;
    }

    public boolean hasValidTipo() {
        return tipo != null && (tipo.equals("DEPOSITO") || tipo.equals("RETIRO") || tipo.equals("TRANSFERENCIA"));
    }

    public boolean hasValidDescripcion() {
        return descripcion != null && !descripcion.isEmpty();
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return String.format(
                "Transaccion{id=%d, cuenta_origen_id=%d, cuenta_destino_id=%d, monto=%s, comision=%s, tipo='%s', descripcion='%s', estado='%s', fecha_creacion=%s, fecha_actualizacion=%s}",
                id, cuenta_origen_id, cuenta_destino_id, monto, comision, tipo, descripcion, estado, fecha_creacion, fecha_actualizacion);
    }

    public void updateDetails(Long cuentaOrigenId, Long cuentaDestinoId, BigDecimal monto, BigDecimal comision, String tipo, String descripcion) {
        if (cuentaOrigenId != null && cuentaOrigenId > 0) {
            this.cuenta_origen_id = cuentaOrigenId;
        }
        if (cuentaDestinoId != null && cuentaDestinoId > 0) {
            this.cuenta_destino_id = cuentaDestinoId;
        }
        if (monto != null && hasValidMonto()) {
            this.monto = monto;
        }
        if (comision != null && hasValidComision()) {
            this.comision = comision;
        }
        if (tipo != null && hasValidTipo()) {
            this.tipo = tipo;
        }
        if (descripcion != null && hasValidDescripcion()) {
            this.descripcion = descripcion;
        }

        this.fecha_actualizacion = LocalDateTime.now();
    }

}
