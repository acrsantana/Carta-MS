package br.com.acrtech.planningpoker.cartas.exception;

public class CartaNaoEncontradaException extends RuntimeException {
    public CartaNaoEncontradaException(String message) {
        super(message);
    }

    public CartaNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }
}
