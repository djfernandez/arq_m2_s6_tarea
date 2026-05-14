package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transacciones")
public class TransaccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaccion_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id", nullable = false)
    private CuentaEntity cuentaOrigen;
    
    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id", nullable = false)
    private CuentaEntity cuentaDestino;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal comision;
    
    @Column(nullable = false, length = 20)
    private String tipo; // "DEPOSITO" o "RETIRO"

    @Column(length = 255)
    private String descripcion;
    
    @Column(nullable = false, length = 20)
    private String estado;
    
    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false, nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;
}
