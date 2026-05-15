package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.FindClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.CreateCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.FindCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidCuentaDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.ClienteRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.CuentaCrearRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.CuentaRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.CuentaResponse;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.CuentaMapper;

@RestController
@RequestMapping("/api/cuentas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class CuentaController {

    private final FindClienteUseCase findClienteUseCase;
    private final CreateCuentaUseCase createCuentaUseCase;
    private final FindCuentaUseCase findCuentaUseCase;
    // private final FindTransaccionUseCase findTransaccionUseCase;
    private final CuentaMapper cuentaMapper;

    @PostMapping
    public ResponseEntity<CuentaResponse> createCuenta(@RequestBody CuentaCrearRequest request) {
        log.info("Recibida solicitud para crear una cuenta");
        try {            
            Cliente cliente = findClienteUseCase.findClienteByNombreIgnoreCase(request.getNombre());

            if (cliente == null) {
                log.warn("No se encontró ningún cliente con el nombre: {}", request.getNombre());
                return ResponseEntity.notFound().build();
            }

            if (request.getSaldo() == null || request.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
                log.warn("Saldo inválido para la cuenta: {}", request.getSaldo());
                return ResponseEntity.badRequest().build();
            }

            log.info("Cliente encontrado: {}", cliente.toString());

            CuentaRequest cuentaRequest = CuentaRequest.builder()
                    .cliente_id(cliente.getId())
                    .numeroCuenta(findCuentaUseCase.generarNumeroCuenta())
                    .saldo(request.getSaldo())
                    .estado("ACTIVO") // O el estado que corresponda según tu lógica de negocio
                    .build();

            log.info("Creando cuenta con los siguientes datos: {}", cuentaRequest.toString());
            Cuenta cuentaMap = cuentaMapper.toDomain(cuentaRequest);
            log.info("Creando cuentaMAP con los siguientes datos: {}", cuentaMap.toString());
            Cuenta cuenta = createCuentaUseCase.execute(cuentaMap);
            return ResponseEntity.ok(cuentaMapper.toResponse(cuenta));
        } catch (InvalidCuentaDataException e) {
            log.error("Datos de cuenta inválidos: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("Error al crear cuenta", e);
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> getAllCuentas() {
        try {
            log.info("Recibida solicitud para obtener todas las cuentas");
            List<Cuenta> cuentas = findCuentaUseCase.findAllCuentas();
            log.info(cuentas.toString());
            List<CuentaResponse> response = this.cuentaMapper.toResponse(cuentas);
            log.info("Cuentas encontradas: {}", cuentas.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener cuentas", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/saldo")
    public ResponseEntity<List<CuentaResponse>> getCuentasBySaldo(@RequestBody ClienteRequest request) {
        log.info("Recibida solicitud para obtener cuentas por saldo para el cliente: {}", request.getNombre());
        try {
            Cliente cliente = findClienteUseCase.findClienteByNombreIgnoreCase(request.getNombre());

            if (cliente == null) {
                log.warn("No se encontró ningún cliente con el nombre: {}", request.getNombre());
                return ResponseEntity.notFound().build();
            }

            log.info("Cliente encontrado: {}", cliente.toString());

            List<Cuenta> cuentas = findCuentaUseCase.findCuentasByClienteId(cliente.getId());

            // BigDecimal saldoActualizado = BigDecimal.ZERO;

            // for (Cuenta cuenta : cuentas) {
            // BigDecimal totalIngreso =
            // findTransaccionUseCase.totalIngresoTransaccionesByCuentaOrigen(cuenta.getNumeroCuenta());
            // BigDecimal totalRetiro =
            // findTransaccionUseCase.totalRetiroTransaccionesByCuentaOrigen(cuenta.getNumeroCuenta());
            // saldoActualizado = totalIngreso.subtract(totalRetiro);

            // log.info("Cuenta: {}, Total Ingreso: {}, Total Retiro: {}, Saldo Actualizado:
            // {}", cuenta.getNumeroCuenta(), totalIngreso, totalRetiro, saldoActualizado);
            // }

            List<CuentaResponse> response = this.cuentaMapper.toResponse(cuentas);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener cuentas por saldo", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
