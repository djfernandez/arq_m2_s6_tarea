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

    public void validateCuentaInput() {
        if (!hasValidCliente_id()) {
            throw new IllegalArgumentException("El ID del cliente es inválido");
        }

        if (!hasValidNumeroCuenta()) {
            throw new IllegalArgumentException("El número de cuenta es inválido");
        }

        if (!hasValidSaldo()) {
            throw new IllegalArgumentException("El saldo es inválido");
        }
    }

    public boolean hasValidCliente_id() {
        return cliente_id != null && cliente_id > 0;
    }

    public boolean hasValidNumeroCuenta() {
        return numeroCuenta != null &&
                numeroCuenta.matches("\\d{8,15}");
    }

    public boolean hasValidSaldo() {
        return saldo != null && saldo.compareTo(BigDecimal.ZERO) >= 0;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return String.format(
                "Cuenta{id=%d, cliente_id=%d, numeroCuenta='%s', saldo=%s, estado='%s', fecha_creacion=%s, fecha_actualizacion=%s}",
                id, cliente_id, numeroCuenta, saldo, estado, fecha_creacion, fecha_actualizacion);
    }

    public void updateDetails(Long cliente_id, String numeroCuenta, BigDecimal saldo) {
        if (cliente_id != null && cliente_id > 0) {
            this.cliente_id = cliente_id;
        }
        if (numeroCuenta != null && hasValidNumeroCuenta()) {
            this.numeroCuenta = numeroCuenta;
        }
        if (saldo != null && hasValidSaldo()) {
            this.saldo = saldo;
        }

        this.fecha_actualizacion = LocalDateTime.now();
    }
}
