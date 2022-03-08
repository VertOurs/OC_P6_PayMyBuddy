package fr.vertours.buddtwo.exception;

public class InvalidTransferException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Your request is missing information";
    }
}
