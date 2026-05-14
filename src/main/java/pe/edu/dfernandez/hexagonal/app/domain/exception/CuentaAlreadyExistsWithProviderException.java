package pe.edu.dfernandez.hexagonal.app.domain.exception;

public class CuentaAlreadyExistsWithProviderException extends RuntimeException {
    public CuentaAlreadyExistsWithProviderException(String message) {
        super("A cuenta already exists with provider: " + message);
    }
}