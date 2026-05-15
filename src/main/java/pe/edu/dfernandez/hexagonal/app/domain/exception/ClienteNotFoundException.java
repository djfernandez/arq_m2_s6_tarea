package pe.edu.dfernandez.hexagonal.app.domain.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(Long id) {
        super("Cliente not found: " + id);
    }
    public ClienteNotFoundException(String numeroCuenta) {
        super("Cliente not found: " + numeroCuenta);
    }
}
