package com.gsolano.gnotes.DataBase.RealmModels;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RmNotes extends RealmObject {
    @PrimaryKey
    private int id;
    String Titulo;
    String Nota;
    String User;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getNota() {
        return Nota;
    }

    public void setNota(String nota) {
        Nota = nota;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
