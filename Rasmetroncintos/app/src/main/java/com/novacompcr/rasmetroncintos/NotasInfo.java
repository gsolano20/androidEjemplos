package com.novacompcr.rasmetroncintos;


public class NotasInfo {
    private String id;
    private String IdSolicitud;
    private String Descripcion;
    private String Titulo;

    public NotasInfo(String id, String IdSolicitud, String Titulo, String Descripcion){
        this.id     =id   ;
        this.IdSolicitud=IdSolicitud   ;
        this.Descripcion =Descripcion   ;
        this.Titulo=Titulo   ;
    }

    public String getid(){          return id;    }
    public String getIdSolicitud(){ return IdSolicitud;    }
    public String getDescripcion(){ return Descripcion;    }
    public String getTitulo(){     return Titulo;    }
}

