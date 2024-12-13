package com.pa.gav.Exeptions;

public class UsernameDuplicadoException extends RuntimeException{

    public UsernameDuplicadoException(String mensaje){
        super(mensaje);
    }
}
