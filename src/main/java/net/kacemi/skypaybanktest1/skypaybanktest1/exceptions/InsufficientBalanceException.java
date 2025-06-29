package net.kacemi.skypaybanktest1.skypaybanktest1.exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
