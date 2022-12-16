package br.com.acrtech.planningpoker.cartas.exception;

public class ErroAoSalvarCartasException extends RuntimeException {
    public ErroAoSalvarCartasException(String message) {
        super(message);
    }

    public ErroAoSalvarCartasException(String message, Throwable cause) {
        super(message, cause);
    }
}
