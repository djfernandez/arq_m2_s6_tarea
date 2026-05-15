package pe.edu.dfernandez.hexagonal.app.application.usecase.Cuenta;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.FindCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.output.CuentaRepositoryPort;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;

@RequiredArgsConstructor
@Transactional
@Slf4j
public class FindCuentaUseCaseImpl implements FindCuentaUseCase {

    private final CuentaRepositoryPort cuentaRepository;

    @Override
    @Transactional(readOnly = true)
    public Cuenta findCuentaById(Long id) {
        if (id == null) {
            log.error("El ID del cliente no puede ser nulo");
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
        }

        return cuentaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("La cuenta con ID {} no existe", id);
                    return new ClienteNotFoundException(id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findAllCuentas() {
        log.info("Buscando todas las cuentas");
        List<Cuenta> cuentas = cuentaRepository.findAll();
        log.info("Se encontraron {} cuentas", cuentas.size());
        return cuentas;
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findCuentasByNumeroCuenta(String numeroCuenta) {
        log.info("Buscando cuentas por número de cuenta: {}", numeroCuenta);

        if (numeroCuenta == null || numeroCuenta.isEmpty()) {
            log.error("El número de cuenta no puede ser nulo o vacío");
            throw new InvalidClienteDataException("El número de cuenta no puede ser nulo o vacío");
        }

        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> {
                    log.error("La cuenta con número de cuenta {} no existe", numeroCuenta);
                    return new ClienteNotFoundException(numeroCuenta);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public String generarNumeroCuenta() {
        log.info("Generando un nuevo número de cuenta");
        Long maxId = cuentaRepository.maxId();
        long siguienteCorrelativo = (maxId != null) ? maxId + 1 : 1L;
        String nuevoNumeroCuenta = String.format("%08d", siguienteCorrelativo);
        log.info("Nuevo número de cuenta generado: {}", nuevoNumeroCuenta);
        return nuevoNumeroCuenta;
    }

    @Override
    public List<Cuenta> findCuentasByClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }
}
