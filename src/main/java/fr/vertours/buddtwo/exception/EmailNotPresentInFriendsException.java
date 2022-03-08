package fr.vertours.buddtwo.exception;


public class EmailNotPresentInFriendsException extends RuntimeException {

    private final String email;

    public EmailNotPresentInFriendsException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return "No friend uses this email : " + email;
    }
}
