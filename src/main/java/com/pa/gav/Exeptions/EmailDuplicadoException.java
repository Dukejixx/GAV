package com.pa.gav.Exeptions;

public class EmailDuplicadoException extends RuntimeException{
    public EmailDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
