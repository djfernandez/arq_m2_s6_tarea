package pe.edu.dfernandez.hexagonal.app.domain.exception;

public class ClienteAlreadyExistsWithProviderException extends RuntimeException {
    public ClienteAlreadyExistsWithProviderException(String message) {
        super("A cliente already exists with provider: " + message);
    }
}
