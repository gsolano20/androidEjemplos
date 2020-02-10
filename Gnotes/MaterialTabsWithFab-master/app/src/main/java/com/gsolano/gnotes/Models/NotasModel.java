package com.gsolano.gnotes.Models;

public class NotasModel {
    int Id;
    String Titulo;
    String Nota;
    String User;

    public NotasModel(int id, String titulo, String nota,String user) {
        Id = id;
        Titulo = titulo;
        Nota = nota;
        User = user;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
