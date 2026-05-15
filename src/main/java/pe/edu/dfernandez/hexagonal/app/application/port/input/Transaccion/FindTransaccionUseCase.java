package pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion;

import java.math.BigDecimal;
import java.util.List;

import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;

public interface FindTransaccionUseCase {
    Transaccion findTransaccionById(Long id);

    List<Transaccion> findAllTransacciones();

    List<Transaccion> findTransaccionesByCuentaOrigen(String cuentaOrigen);

    List<Transaccion> findTransaccionesByCuentaDestino(String cuentaDestino);

    BigDecimal totalIngresoTransaccionesByCuentaOrigen(String cuentaOrigen);
    
    BigDecimal totalRetiroTransaccionesByCuentaOrigen(String cuentaOrigen);
}
