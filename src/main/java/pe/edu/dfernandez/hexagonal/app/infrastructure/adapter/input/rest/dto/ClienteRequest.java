package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {
    private String nombre;
    private String email;
    private String documento;
}
