package cat.itacademy.s04.t01.userapi.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Error afegint usuari. El email ja existeix.");
    }
}
