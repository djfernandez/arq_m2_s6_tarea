
package pe.edu.dfernandez.hexagonal.app.domain.exception;

public class InvalidCuentaDataException extends RuntimeException {
    public InvalidCuentaDataException(String message) {
        super("Invalid Cuenta Data: " + message);
    }
}