package com.novacompcr.rasmetroncintos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class NotasAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList datos;

    public NotasAdapter(Context context1, ArrayList arraylist) {
        super(context1, R.layout.nota_item, arraylist);
        context = context1;
        datos = arraylist;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        view = LayoutInflater.from(context).inflate(R.layout.nota_item, null);

       // TextView id = (TextView) view.findViewById(R.id.txtServicio);
        TextView Titulo = (TextView) view.findViewById(R.id.txtTitulo);
        TextView Descripcion = (TextView) view.findViewById(R.id.txtDescripcion);



        Titulo.setText(((NotasInfo) datos.get(i)).getTitulo());
        Descripcion.setText(((NotasInfo) datos.get(i)).getDescripcion());


        return view;
    }



}
