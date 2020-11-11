package pl.lasota.msg;

public class UserAlreadyRegistryException extends RuntimeException {
    public UserAlreadyRegistryException(String message) {
        super(message);
    }
}
