package fr.vertours.buddtwo.exception;

public class EmailAlreadyPresentException  extends RuntimeException {

    private final String EMAIL;

    public EmailAlreadyPresentException(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    @Override
    public String getMessage() {
        return "This email \"" + EMAIL + "\", was already use in application";
    }
}
