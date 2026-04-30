package cat.itacademy.s04.t01.userapi.exception;

public class UserIdDoesNotExists extends RuntimeException {
    public UserIdDoesNotExists() {
        super("El id de usuari no existeix.");
    }
}
