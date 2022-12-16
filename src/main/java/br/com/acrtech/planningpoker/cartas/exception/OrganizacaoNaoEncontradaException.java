package br.com.acrtech.planningpoker.cartas.exception;

public class OrganizacaoNaoEncontradaException extends RuntimeException {
    public OrganizacaoNaoEncontradaException(String message) {
        super(message);
    }

    public OrganizacaoNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }
}
