package pe.edu.dfernandez.hexagonal.app.domain.exception;

public class InvalidTransaccionDataException extends RuntimeException {
    public InvalidTransaccionDataException(String message) {
        super("Invalid Transaccion Data: " + message);
    }
}
