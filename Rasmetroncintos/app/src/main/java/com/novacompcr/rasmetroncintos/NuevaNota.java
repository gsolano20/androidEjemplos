package com.novacompcr.rasmetroncintos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class NuevaNota extends Activity {
    Context context;
    private MySQLiteHelper db;
    private String DirigidoA,Autoriza,nombre;
    private RequestQueue queue;
    private EditText EdtJefaturaAutoriza;

    ArrayList<String> list;
    ListView listView;

    Dialog ViewAutoriza,ViewDirigido;
    private EditText EdtTitulo,EdtDescripcion;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_nota);
        context = this;

        db = new MySQLiteHelper(context);
         EdtTitulo=(EditText)findViewById(R.id.edtTitulo);
        EdtDescripcion=(EditText)findViewById(R.id.edtDescripcion);



        queue= Volley.newRequestQueue(this);


    }


    public void grabarNota(View view) {
        Integer id;
        db.GetMaxNota();
        if(db.GetMaxNota()!= null && !db.GetMaxNota().isEmpty() ){
            id=Integer.valueOf(db.GetMaxNota())+1;
        }else{
            id=0;
        }

        db.SetNotas(String.valueOf(id),"",String.valueOf(EdtTitulo.getText()),String.valueOf(EdtDescripcion.getText()));
        Intent GoNotas = new Intent(context, NotasActivity.class);
        startActivity(GoNotas);
    }
}
