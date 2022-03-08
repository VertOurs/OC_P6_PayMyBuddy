package fr.vertours.buddtwo.exception;

public class NoEnoughMoneyException extends RuntimeException{
    @Override
    public String getMessage() {
        return "the account does not have enough money for this transfer";
    }
}
