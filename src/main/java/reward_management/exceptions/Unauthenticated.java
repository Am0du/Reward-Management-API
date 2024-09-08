package reward_management.exceptions;

public class Unauthenticated extends RuntimeException {

    public Unauthenticated(String message) {
        super(message);
    }
}
