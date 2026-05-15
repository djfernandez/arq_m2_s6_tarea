package pe.edu.dfernandez.hexagonal.app.application.usecase.Transaccion;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.FindTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.TransaccionRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class FindTransaccionUseCaseImpl implements FindTransaccionUseCase {

    private final TransaccionRepositoryPort transaccionRepository;

    @Override
    @Transactional(readOnly = true)
    public Transaccion findTransaccionById(Long id) {
        if (id == null) {
            log.error("El ID de la transacción no puede ser nulo");
            throw new IllegalArgumentException("El ID de la transacción no puede ser nulo");
        }

        return transaccionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("La transacción con ID {} no existe", id);
                    return new ClienteNotFoundException(id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaccion> findAllTransacciones() {
        log.info("Buscando todas las transacciones");
        List<Transaccion> transacciones = transaccionRepository.findAll();
        log.info("Se encontraron {} transacciones", transacciones.size());
        return transacciones;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaccion> findTransaccionesByCuentaOrigen(String cuentaOrigen) {
        log.info("Buscando transacciones por cuenta de origen: {}", cuentaOrigen);

        if (cuentaOrigen == null || cuentaOrigen.isEmpty()) {
            log.error("La cuenta de origen no puede ser nula o vacía");
            throw new IllegalArgumentException("La cuenta de origen no puede ser nula o vacía");
        }

        List<Transaccion> transacciones = transaccionRepository.findByCuentaOrigenId(1L);
        log.info("Se encontraron {} transacciones con la cuenta de origen {}", transacciones.size(), cuentaOrigen);
        return transacciones;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaccion> findTransaccionesByCuentaDestino(String cuentaDestino) {
        log.info("Buscando transacciones por cuenta de destino: {}", cuentaDestino);

        if (cuentaDestino == null || cuentaDestino.isEmpty()) {
            log.error("La cuenta de destino no puede ser nula o vacía");
            throw new IllegalArgumentException("La cuenta de destino no puede ser nula o vacía");
        }

        List<Transaccion> transacciones = transaccionRepository.findByCuentaDestinoId(1L);
        log.info("Se encontraron {} transacciones con la cuenta de destino {}", transacciones.size(), cuentaDestino );
        return transacciones;
    }

    @Override
    public BigDecimal totalIngresoTransaccionesByCuentaOrigen(String cuentaOrigen) {
        log.info("Calculando el total de ingresos por transacciones con cuenta de origen: {}", cuentaOrigen);
        if (cuentaOrigen == null || cuentaOrigen.isEmpty()) {
            log.error("La cuenta de origen no puede ser nula o vacía");
            throw new IllegalArgumentException("La cuenta de origen no puede ser nula o vacía");
        }
        BigDecimal total = transaccionRepository.totalIngresoByCuentaOrigen(cuentaOrigen);
        log.info("El total de ingresos por transacciones con cuenta de origen {} es {}", cuentaOrigen, total);
        return total;
    }

    @Override
    public BigDecimal totalRetiroTransaccionesByCuentaOrigen(String cuentaOrigen) {
        log.info("Calculando el total de retiros por transacciones con cuenta de origen: {}", cuentaOrigen);
        if (cuentaOrigen == null || cuentaOrigen.isEmpty()) {
            log.error("La cuenta de origen no puede ser nula o vacía");
            throw new IllegalArgumentException("La cuenta de origen no puede ser nula o vacía");
        }
        BigDecimal total = transaccionRepository.totalRetiroByCuentaOrigen(cuentaOrigen);
        log.info("El total de retiros por transacciones con cuenta de origen {} es {}", cuentaOrigen, total);
        return total;
    }
    
}
