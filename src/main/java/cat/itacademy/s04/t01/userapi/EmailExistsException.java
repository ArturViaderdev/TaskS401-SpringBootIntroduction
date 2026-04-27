package cat.itacademy.s04.t01.userapi;

public class EmailExistsException extends Exception{
    public EmailExistsException()
    {
        super("Error afegint usuari. El email ja existeix.");
    }
}
