package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.web.controller;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.FindClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.CreateCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.FindCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cuenta.UpdateCuentaUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Transaccion.CreateTransaccionUseCase;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cuenta;
import pe.edu.dfernandez.hexagonal.app.domain.model.Transaccion;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.CuentaCrearRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.CuentaRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.TransferenciaFormRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.CuentaMapper;

@Controller
@RequestMapping("/cuentas")
@RequiredArgsConstructor
@Slf4j
public class CuentaViewController {

    private final FindClienteUseCase findClienteUseCase;
    private final FindCuentaUseCase findCuentaUseCase;
    private final CreateCuentaUseCase createCuentaUseCase;
    private final UpdateCuentaUseCase updateCuentaUseCase;
    private final CreateTransaccionUseCase createTransaccionUseCase;
    private final CuentaMapper cuentaMapper;

    @GetMapping
    public String viewCuentas(@RequestParam(required = false) String nombre, Model model) {
        List<Cuenta> cuentas = Collections.emptyList();
        BigDecimal saldoTotal = BigDecimal.ZERO;

        if (nombre != null && !nombre.isBlank()) {
            try {
                Cliente cliente = findClienteUseCase.findClienteByNombreIgnoreCase(nombre.trim());

                if (cliente == null) {
                    model.addAttribute("errorMessage", "No existe un cliente con ese nombre.");
                } else {
                    cuentas = findCuentaUseCase.findCuentasByClienteId(cliente.getId());
                    saldoTotal = cuentas.stream()
                            .map(cuenta -> cuenta.getSaldo() == null ? BigDecimal.ZERO : cuenta.getSaldo())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    model.addAttribute("clienteEncontrado", cliente.getNombre());
                }
            } catch (Exception e) {
                log.warn("No se pudo buscar el saldo por nombre {}: {}", nombre, e.getMessage());
                model.addAttribute("errorMessage", "No se pudo consultar el saldo para el nombre ingresado.");
            }
        }

        model.addAttribute("cuentas", cuentas);
        model.addAttribute("saldoTotal", saldoTotal);
        model.addAttribute("filtroNombre", nombre == null ? "" : nombre);
        model.addAttribute("cuentaForm", new CuentaCrearRequest());
        model.addAttribute("transferenciaForm", new TransferenciaFormRequest());

        return "cuentas";
    }

    @PostMapping
    public String createCuenta(@ModelAttribute("cuentaForm") CuentaCrearRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            if (request.getNombre() == null || request.getNombre().isBlank()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Debe ingresar un nombre.");
                return "redirect:/cuentas";
            }

            if (request.getSaldo() == null || request.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "El saldo debe ser mayor o igual a 0.");
                return "redirect:/cuentas";
            }

            Cliente cliente = findClienteUseCase.findClienteByNombreIgnoreCase(request.getNombre().trim());
            if (cliente == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "No existe un cliente con ese nombre.");
                return "redirect:/cuentas";
            }

            CuentaRequest cuentaRequest = CuentaRequest.builder()
                    .cliente_id(cliente.getId())
                    .numeroCuenta(findCuentaUseCase.generarNumeroCuenta())
                    .saldo(request.getSaldo())
                    .estado("ACTIVO")
                    .build();

            Cuenta cuenta = cuentaMapper.toDomain(cuentaRequest);
            createCuentaUseCase.execute(cuenta);

            redirectAttributes.addFlashAttribute("successMessage", "Cuenta creada correctamente.");
            String nombreCodificado = UriUtils.encodePathSegment(cliente.getNombre(), "UTF-8");
            return "redirect:/cuentas?nombre=" + nombreCodificado;
        } catch (IllegalArgumentException e) {
            log.warn("Error de validacion al crear cuenta: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al crear cuenta", e);
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo crear la cuenta.");
        }

        return "redirect:/cuentas";
    }

    @PostMapping("/transferencia")
    @Transactional
    public String createTransferencia(@ModelAttribute("transferenciaForm") TransferenciaFormRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            if (request.getCuentaOrigen() == null || request.getCuentaOrigen().isBlank()
                    || request.getCuentaDestino() == null || request.getCuentaDestino().isBlank()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Debe ingresar ambas cuentas.");
                return "redirect:/cuentas";
            }

            if (request.getMonto() == null || request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "El monto debe ser mayor a 0.");
                return "redirect:/cuentas";
            }

            if (request.getComision() == null || request.getComision().compareTo(BigDecimal.ZERO) < 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "La comisión debe ser mayor o igual a 0.");
                return "redirect:/cuentas";
            }

            if (request.getCuentaOrigen().trim().equals(request.getCuentaDestino().trim())) {
                redirectAttributes.addFlashAttribute("errorMessage", "La cuenta de origen y destino no pueden ser la misma.");
                return "redirect:/cuentas";
            }

            Cuenta cuentaOrigen = findCuentaUseCase.findCuentasByNumeroCuenta(request.getCuentaOrigen().trim());
            Cuenta cuentaDestino = findCuentaUseCase.findCuentasByNumeroCuenta(request.getCuentaDestino().trim());

            if (cuentaOrigen == null || cuentaDestino == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontró una o ambas cuentas.");
                return "redirect:/cuentas";
            }

            BigDecimal totalDebitar = request.getMonto().add(request.getComision());
            if (cuentaOrigen.getSaldo() == null || cuentaOrigen.getSaldo().compareTo(totalDebitar) < 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "La cuenta de origen no tiene saldo suficiente para el monto más la comisión.");
                return "redirect:/cuentas?nombre=" + UriUtils.encodePathSegment(findClienteUseCase
                        .findClienteById(cuentaOrigen.getCliente_id()).getNombre(), "UTF-8");
            }

            BigDecimal nuevoSaldoOrigen = cuentaOrigen.getSaldo().subtract(totalDebitar);
            BigDecimal nuevoSaldoDestino = cuentaDestino.getSaldo().add(request.getMonto());

            Cuenta cuentaOrigenActualizada = cuentaMapper.toDomain(CuentaRequest.builder()
                    .id(cuentaOrigen.getId())
                    .cliente_id(cuentaOrigen.getCliente_id())
                    .numeroCuenta(cuentaOrigen.getNumeroCuenta())
                    .saldo(nuevoSaldoOrigen)
                    .estado(cuentaOrigen.getEstado())
                    .build());

            Cuenta cuentaDestinoActualizada = cuentaMapper.toDomain(CuentaRequest.builder()
                    .id(cuentaDestino.getId())
                    .cliente_id(cuentaDestino.getCliente_id())
                    .numeroCuenta(cuentaDestino.getNumeroCuenta())
                    .saldo(nuevoSaldoDestino)
                    .estado(cuentaDestino.getEstado())
                    .build());

            updateCuentaUseCase.execute(cuentaOrigenActualizada.getId(), cuentaOrigenActualizada);
            updateCuentaUseCase.execute(cuentaDestinoActualizada.getId(), cuentaDestinoActualizada);

            Transaccion transaccion = Transaccion.builder()
                    .cuenta_origen_id(cuentaOrigen.getId())
                    .cuenta_destino_id(cuentaDestino.getId())
                    .monto(request.getMonto())
                    .comision(request.getComision())
                    .tipo("TRANSFERENCIA")
                    .descripcion("Transferencia entre cuentas")
                    .estado("COMPLETADA")
                    .build();

            createTransaccionUseCase.execute(transaccion);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Transferencia realizada correctamente. Comisión aplicada: S/ "
                            + request.getComision().toPlainString());
            String nombreCodificado = UriUtils.encodePathSegment(findClienteUseCase
                    .findClienteById(cuentaOrigen.getCliente_id()).getNombre(), "UTF-8");
            return "redirect:/cuentas?nombre=" + nombreCodificado;
        } catch (IllegalArgumentException e) {
            log.warn("Error de validacion al crear transferencia: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al crear transferencia", e);
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo realizar la transferencia.");
        }

        return "redirect:/cuentas";
    }
}