package com.gsolano.gnotes.DataBase.RealmModels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RmUsers extends RealmObject {
    @PrimaryKey
    private int id;
    String Nombre;
    String Correo;
    String Clave;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
