package br.com.acrtech.planningpoker.cartas.exception;

public class ErroAoRecuperarCartasException extends RuntimeException {
    public ErroAoRecuperarCartasException(String message) {
        super(message);
    }

    public ErroAoRecuperarCartasException(String message, Throwable cause) {
        super(message, cause);
    }
}
