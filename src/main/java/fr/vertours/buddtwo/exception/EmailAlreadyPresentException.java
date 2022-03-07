package fr.vertours.buddtwo.exception;

public class EmailAlreadyPresentException  extends RuntimeException {

    private final String email;

    public EmailAlreadyPresentException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return "This email \"" + email + "\", was already use in application";
    }
}
