package com.androiddesdecero.ottobus;

/**
 * Created by albertopalomarrobledo on 11/4/18.
 */

public class MessageFtoA {

    private String message;
    private String texto;

    public MessageFtoA(String message,String texto){
        this.message = message;
        this.texto = texto;
    }

    public String getMessage(){
        return message;

    }public String getTexto(){
        return texto;

    }
}
