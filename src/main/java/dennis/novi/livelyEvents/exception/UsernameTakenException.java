package dennis.novi.livelyEvents.exception;


public class UsernameTakenException extends RuntimeException{
    public UsernameTakenException() { super();}
    public UsernameTakenException(String message) { super(message);}
}
