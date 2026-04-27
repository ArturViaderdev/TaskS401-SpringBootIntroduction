package cat.itacademy.s04.t01.userapi;

public class EmailAlreadyExistsException extends Exception{
    public EmailAlreadyExistsException()
    {
        super("Error afegint usuari. El email ja existeix.");
    }
}
