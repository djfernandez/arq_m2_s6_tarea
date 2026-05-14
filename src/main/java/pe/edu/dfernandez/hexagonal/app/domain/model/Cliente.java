package pe.edu.dfernandez.hexagonal.app.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Cliente {

    private Long id;

    private String nombre;

    private String email;

    private String documento;

    private String estado;

    private LocalDateTime fecha_creacion;

    private LocalDateTime fecha_actualizacion;

    public void validateClienteInput() {
        if (!hasValidNombre()) {
            throw new IllegalArgumentException("El nombre del cliente es inválido");
        }

        if (!hasValidEmail()) {
            throw new IllegalArgumentException("El email del cliente es inválido");
        }

        if (!hasValidDocumento()) {
            throw new IllegalArgumentException("El documento del cliente es inválido");
        }
    }

    public boolean hasValidEmail() {
        return email != null &&
                email.contains("@") &&
                email.contains(".") &&
                email.length() > 5;
    }

    public boolean hasValidDocumento() {
        return documento != null &&
                documento.matches("\\d{8,15}");
    }

    public boolean hasValidNombre() {
        return nombre != null &&
                !nombre.trim().isEmpty() &&
                nombre.length() >= 3;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return String.format(
                "Cliente{id=%d, nombre='%s', email='%s', documento='%s'}",
                id, nombre, email, documento);
    }

    public void updateDetails(String nombre, String email, String documento) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
        if (email != null && hasValidEmail()) {
            this.email = email;
        }
        if (documento != null && hasValidDocumento()) {
            this.documento = documento;
        }

        this.fecha_actualizacion = LocalDateTime.now();
    }
}
