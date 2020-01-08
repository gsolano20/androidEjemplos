package com.novacompcr.rasmetroncintos;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class NotasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Context context;
    private String position;
    private ProgressDialog progressDialog;
    private StringRequest stringRequest;
    private RequestQueue queue;
    private MySQLiteHelper db;
   // private ListView LvSolicitud;
    private SwipeMenuListView LvSolicitud;
    private NotasInfo NotasDetail;
    private Button BtnMenu;
    private Dialog dialog;
    private String TAG;
    private  View SolicitudItem;
    private Boolean Encrypado;

    private String IdSolicitud,Descripcion, Titulo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notas);
        getSupportActionBar().hide();
        context=this;
        db = new MySQLiteHelper(context);
        Bundle extras = getIntent().getExtras();
        queue= Volley.newRequestQueue(this);
        if(extras!=null) {
            position = extras.getString("position");
        }
        //MostrarSolicitudes();
        MostrarSolicitudes();
         LvSolicitud = (SwipeMenuListView) findViewById(R.id.lvSolicitudes);
        //LvSolicitud=findViewById(R.id.lvSolicitudes);
        BtnMenu=findViewById(R.id.btnMenu);

    }

    public  void MostrarSolicitudes(){
        //db.ReinitSolicitudes();
        //db.SetSolicitudes("0", "N0303" , "Cambio de computadora"  , "warning"  );
        //db.SetSolicitudes("1", "N0304" , "Arreglo de puerta"  , "success"  );
        db.getNotas();
        NotasAdapter NotasAdapter = new NotasAdapter(this, db.NotasItems);
        SwipeMenuListView LvSolicitud = (SwipeMenuListView) findViewById(R.id.lvSolicitudes);
        LvSolicitud.setAdapter(NotasAdapter);
        LvSolicitud.setOnItemClickListener(this);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.close);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        LvSolicitud.setMenuCreator(creator);

        LvSolicitud.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Log.d(TAG, "onMenuItemClick: clicked item " + index);
                    break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        db.getNotasDetalle(String.valueOf(position));
        NotasDetail = db.NotasDetail;
        Titulo = NotasDetail.getTitulo();
        Descripcion = NotasDetail.getDescripcion();
        alertaSuccess();
    }

    public void alertaSuccess(){
        Encrypado=false;
        SolicitudItem = LayoutInflater.from(context).inflate(R.layout.notas_itemssucces, null);
        dialog=new Dialog(context);
        dialog.setContentView(SolicitudItem);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView NumSolicitud = (TextView)SolicitudItem.findViewById(R.id.txtTituloNota);
        final EditText TxtDescripcion = (EditText)SolicitudItem.findViewById(R.id.txtDescripcionNota);
        TextView Txtview = (TextView)SolicitudItem.findViewById(R.id.txtview);
        Button BtnAsignar = (Button)SolicitudItem.findViewById(R.id.btnRegresar);
        NumSolicitud.setText(Titulo);
        TxtDescripcion.setText(Descripcion);
        BtnAsignar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                dialog.cancel();
            }
        });
        Txtview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(!Encrypado){
                    TxtDescripcion.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Encrypado=!Encrypado;
                }else{
                    TxtDescripcion.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
                    Encrypado=!Encrypado;
                }
            }
        });
    }

    public void openMenuSolicitudes(View view) {
       // getMenuInflater().inflate(R.menu.solicitudmenu, (android.view.Menu) Menu);
        View SolicitudItem = LayoutInflater.from(context).inflate(R.layout.nota_menu, null);
        dialog=new Dialog(context);
        dialog.setContentView(SolicitudItem);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView cambiarPerfil = (TextView)SolicitudItem.findViewById(R.id.cambiarPerfil);
        TextView crearSolicitud = (TextView)SolicitudItem.findViewById(R.id.crearSolicitud);
        TextView consultarSolicitud = (TextView)SolicitudItem.findViewById(R.id.consultarSolicitud);
        TextView cerrarseccion = (TextView)SolicitudItem.findViewById(R.id.cerrarseccion);

        cambiarPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                dialog.cancel();
            }
        });
        crearSolicitud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                dialog.cancel();
            }

        });
        consultarSolicitud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                dialog.cancel();
            }
        });
        cerrarseccion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               // Intent GoLogin = new Intent(context, test.class);
               // startActivity(GoLogin);
                dialog.cancel();
            }
        });

    }

    public void openNewSolicitud(View view) {
        Intent NewSolicitud = new Intent(context, NuevaNota.class);
        startActivity(NewSolicitud);
    }
    /*public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.optiona:
                return true;
            case R.id.optionb:
                return true;
            case R.id.optionc:
                return true;
            case R.id.optiond:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }*/
}
