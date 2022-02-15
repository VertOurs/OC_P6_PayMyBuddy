package fr.vertours.buddtwo.exception;

public class PasswordDoesNotMatchException extends RuntimeException {

    @Override
    public String getMessage() {
        return "please enter your current password";
    }
}
