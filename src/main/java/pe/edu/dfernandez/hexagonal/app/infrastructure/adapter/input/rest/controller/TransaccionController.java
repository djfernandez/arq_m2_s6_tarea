package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.FindCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.UpdateCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.CreateTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;
import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.CuentaRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.TransaccionRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.TransaccionResponse;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.CuentaMapper;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.TransaccionMapper;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class TransaccionController {

    private final CreateTransaccionUseCase createTransaccionUseCase;
    private final FindCuentaUseCase findCuentaUseCase;
    private final UpdateCuentaUseCase updateCuentaUseCase;
    private final TransaccionMapper transaccionMapper;
    private final CuentaMapper cuentaMapper;

    @PostMapping
    public ResponseEntity<TransaccionResponse> createTransaccion(@RequestBody TransaccionRequest request) {
        log.info("Recibida solicitud para crear una transacción");

        if (request == null) {
            log.warn("Solicitud de transacción vacía");
            return ResponseEntity.badRequest().build();
        }

        log.info(request.toString());

        if (request.getCuenta_origen_id().equals(request.getCuenta_destino_id())) {
            log.warn("La cuenta de origen y destino no pueden ser la misma: {}", request.getCuenta_origen_id());
            return ResponseEntity.badRequest().build();
        }

        if (request.getCuenta_origen_id() == null || request.getCuenta_destino_id() == null
                || request.getMonto() == null || request.getTipo() == null) {
            log.warn("Datos de transacción incompletos: {}", request);
            return ResponseEntity.badRequest().build();
        }

        if (request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Monto inválido para la transacción: {}", request.getMonto());
            return ResponseEntity.badRequest().build();
        }

        if (!"DEPOSITO".equals(request.getTipo()) && !"RETIRO".equals(request.getTipo())
                && !"TRANSFERENCIA".equals(request.getTipo())) {
            log.warn("Tipo de transacción inválido: {}", request.getTipo());
            return ResponseEntity.badRequest().build();
        }

        Cuenta cuentaOrigen = findCuentaUseCase.findCuentaById(request.getCuenta_origen_id());
        Cuenta cuentaDestino = findCuentaUseCase.findCuentaById(request.getCuenta_destino_id());

        if (cuentaOrigen == null) {
            log.warn("No se encontró la cuenta de origen con ID: {}", request.getCuenta_origen_id());
            return ResponseEntity.notFound().build();
        }

        if (cuentaDestino == null) {
            log.warn("No se encontró la cuenta de destino con ID: {}", request.getCuenta_destino_id());
            return ResponseEntity.notFound().build();
        }

        log.info("Cuenta de origen encontrada: {}", cuentaOrigen.toString());
        log.info("Cuenta de destino encontrada: {}", cuentaDestino.toString());

        if (cuentaOrigen.getSaldo().compareTo(request.getMonto()) < 0) {
            log.warn("Saldo insuficiente en la cuenta de origen (ID: {}) para la transacción de monto: {}",
                    request.getCuenta_origen_id(), request.getMonto());
            return ResponseEntity.badRequest().build();
        }

        request.setEstado("COMPLETADA");
        Transaccion transaccionMap = transaccionMapper.toEntity(request);

        Transaccion transaccion = createTransaccionUseCase.execute(transaccionMap);

        log.info("Transacción creada exitosamente con ID: {}", transaccion.getId());

        CuentaRequest cuentaRequestOrigen = CuentaRequest.builder()
                .id(cuentaOrigen.getId())
                .cliente_id(cuentaOrigen.getCliente_id())
                .numeroCuenta(cuentaOrigen.getNumeroCuenta())
                .saldo(cuentaOrigen.getSaldo())
                .build();

        CuentaRequest cuentaRequestDestino = CuentaRequest.builder()
                .id(cuentaDestino.getId())
                .cliente_id(cuentaDestino.getCliente_id())
                .numeroCuenta(cuentaDestino.getNumeroCuenta())
                .saldo(cuentaDestino.getSaldo())
                .build();

        cuentaRequestOrigen
                .setSaldo(cuentaRequestOrigen.getSaldo().subtract(request.getMonto().add(request.getComision())));
        cuentaRequestDestino.setSaldo(cuentaRequestDestino.getSaldo().add(request.getMonto()));

        log.info("CuentaRequest para cuenta de origen creada: {}", cuentaRequestOrigen.toString());
        log.info("CuentaRequest para cuenta de destino creada: {}", cuentaRequestDestino.toString());

        cuentaOrigen = cuentaMapper.toDomain(cuentaRequestOrigen);
        cuentaDestino = cuentaMapper.toDomain(cuentaRequestDestino);

        updateCuentaUseCase.execute(cuentaOrigen.getId(), cuentaOrigen);
        updateCuentaUseCase.execute(cuentaDestino.getId(), cuentaDestino);

        log.info("Saldo actualizado para la cuenta de origen (ID: {}): {}", cuentaOrigen.getId(),
                cuentaOrigen.getSaldo());
        log.info("Saldo actualizado para la cuenta de destino (ID: {}): {}", cuentaDestino.getId(),
                cuentaDestino.getSaldo());
        // Aquí puedes llamar a tu caso de uso para crear la transacción
        // TransaccionResponse response = createTransaccionUseCase.createTransaccion();

        // Por ahora, devolvemos una respuesta vacía
        return ResponseEntity.ok(transaccionMapper.toResponse(transaccion));
    }

}
