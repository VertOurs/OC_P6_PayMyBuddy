package fr.vertours.buddtwo.exception;

public class EmailNotPresentInApplicationException extends RuntimeException {

    private final String email;

    public EmailNotPresentInApplicationException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return "No one is registered with the email  : " + email;
    }
}
