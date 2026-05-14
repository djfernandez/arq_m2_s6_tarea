package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.CreateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.DeleteClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.FindClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.application.port.input.Cliente.UpdateClienteUseCase;
import pe.edu.dfernandez.hexagonal.app.domain.exception.ClienteNotFoundException;
import pe.edu.dfernandez.hexagonal.app.domain.exception.InvalidClienteDataException;
import pe.edu.dfernandez.hexagonal.app.domain.model.Cliente;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto.ClienteRequest;
import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.mapper.ClienteMapper;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteViewController {

    private final CreateClienteUseCase createClienteUseCase;
    private final FindClienteUseCase findClienteUseCase;
    private final UpdateClienteUseCase updateClienteUseCase;
    private final DeleteClienteUseCase deleteClienteUseCase;
    private final ClienteMapper clienteMapper;

    @GetMapping
    public String listClientes(@RequestParam(required = false) String nombre, Model model) {
        List<Cliente> clientes;

        if (nombre != null && !nombre.isBlank()) {
            clientes = findClienteUseCase.findClientesByNombre(nombre.trim());
        } else {
            clientes = findClienteUseCase.findAllClientes();
        }

        model.addAttribute("clientes", clientes);
        model.addAttribute("clienteForm", new ClienteRequest());
        model.addAttribute("filtroNombre", nombre == null ? "" : nombre);

        return "clientes";
    }

    @PostMapping
    public String createCliente(@ModelAttribute("clienteForm") ClienteRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteMapper.toDomain(request);
            createClienteUseCase.execute(cliente);
            redirectAttributes.addFlashAttribute("successMessage", "Cliente creado correctamente.");
        } catch (InvalidClienteDataException | IllegalArgumentException e) {
            log.warn("No se pudo crear el cliente: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al crear cliente", e);
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo crear el cliente.");
        }

        return "redirect:/clientes";
    }

    @PostMapping("/{id}/editar")
    public String updateCliente(@PathVariable Long id,
            @ModelAttribute("clienteForm") ClienteRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            Cliente cliente = clienteMapper.toDomain(request);
            updateClienteUseCase.execute(id, cliente);
            redirectAttributes.addFlashAttribute("successMessage", "Cliente actualizado correctamente.");
        } catch (InvalidClienteDataException | IllegalArgumentException e) {
            log.warn("No se pudo actualizar el cliente {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (ClienteNotFoundException e) {
            log.warn("No se encontro cliente para actualizar con ID {}", id);
            redirectAttributes.addFlashAttribute("errorMessage", "Cliente no encontrado.");
        } catch (Exception e) {
            log.error("Error inesperado al actualizar cliente {}", id, e);
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo actualizar el cliente.");
        }

        return "redirect:/clientes";
    }

    @PostMapping("/{id}/eliminar")
    public String deleteCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            deleteClienteUseCase.execute(id);
            redirectAttributes.addFlashAttribute("successMessage", "Cliente eliminado correctamente.");
        } catch (IllegalArgumentException | ClienteNotFoundException e) {
            log.warn("No se pudo eliminar el cliente {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al eliminar cliente {}", id, e);
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo eliminar el cliente.");
        }

        return "redirect:/clientes";
    }
}
