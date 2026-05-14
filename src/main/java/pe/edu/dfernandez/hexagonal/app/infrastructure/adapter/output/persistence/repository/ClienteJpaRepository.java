package pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.dfernandez.hexagonal.app.infrastructure.adapter.output.persistence.entity.ClienteEntity;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);

    Optional<ClienteEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT c FROM ClienteEntity c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<ClienteEntity> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);

    // Optional<ClienteEntity> findByDocumento(String documento);
    boolean existsByDocumento(String documento);
}
