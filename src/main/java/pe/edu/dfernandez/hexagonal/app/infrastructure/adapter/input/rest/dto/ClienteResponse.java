package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String email;
    private String documento;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
