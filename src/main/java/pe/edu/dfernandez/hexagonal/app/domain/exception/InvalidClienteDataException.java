
package pe.edu.dfernandez.hexagonal.app.domain.exception;

public class InvalidClienteDataException extends RuntimeException {
    public InvalidClienteDataException(String message) {
        super("Invalid Cliente Data: " + message);
    }
}