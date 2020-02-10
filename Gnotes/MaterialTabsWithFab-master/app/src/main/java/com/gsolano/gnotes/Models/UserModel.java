package com.gsolano.gnotes.Models;

public class UserModel {
    String Nombre;
    String Correo;
    String Clave;

    public UserModel(String nombre, String correo, String clave) {
        Nombre = nombre;
        Correo = correo;
        Clave = clave;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
