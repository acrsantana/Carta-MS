package br.com.acrtech.planningpoker.cartas.exception;

public class CartaNaoInformadaException extends RuntimeException {
    public CartaNaoInformadaException(String message) {
        super(message);
    }

    public CartaNaoInformadaException(String message, Throwable cause) {
        super(message, cause);
    }
}
