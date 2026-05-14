package pe.edu.dfernandez.hexagonal.app.domain.exception;

public class TransaccionNotFoundException extends RuntimeException {
    public TransaccionNotFoundException(String message) {
        super("Transaccion not found: " + message);
    }
}
