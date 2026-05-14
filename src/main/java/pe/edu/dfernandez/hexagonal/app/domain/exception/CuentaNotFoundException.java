package pe.edu.dfernandez.hexagonal.app.domain.exception;

public class CuentaNotFoundException extends RuntimeException {
    public CuentaNotFoundException(String message) {
        super("Cuenta not found: " + message);
    }
}
