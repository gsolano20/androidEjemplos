package com.novacompcr.rasmetroncintos;

public class NotasItem {    private String spoprid      ;
    private String SolITId ;
    private String SolITIdSolicitud  ;
    private String SolITDescripcion ;
    private String SolITTitulo;

    public NotasItem(String Id, String IdSolicitud  , String Titulo, String Descripcion  )
    {
        SolITId          = Id;
        SolITIdSolicitud = IdSolicitud;
        SolITDescripcion = Descripcion;
        Titulo      = Titulo;
    }

    public String getSolITId          (){    return SolITId       ;   }
    public String getSolITIdSolicitud (){    return SolITIdSolicitud  ;   }
    public String getSolITDescripcion (){    return SolITDescripcion   ;   }
    public String getSolITTitulo      (){    return SolITTitulo  ;   }
}
