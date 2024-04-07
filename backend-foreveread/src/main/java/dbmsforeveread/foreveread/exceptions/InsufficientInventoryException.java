package dbmsforeveread.foreveread.exceptions;

public class InsufficientInventoryException extends RuntimeException{
    public InsufficientInventoryException(String message) {
        super(message);
    }
}
