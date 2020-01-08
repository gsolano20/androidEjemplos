package com.novacompcr.rasmetroncintos;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Context context;
    private String res;

    private String User,Pass;
    private EditText EdtUser, EdtPass;
    private Button BtnIngresar;
    private CheckBox ChbxRecordarUsuario;
    private ImageView ImvCCSS,ImvWarning;
    private TextView TxtWarning,TxtOlvidoContrasena;
    private Integer PantallaLogin=0;
    private MySQLiteHelper db;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context=this;
        db = new MySQLiteHelper(context);
        getSupportActionBar().hide();
        EdtUser=findViewById(R.id.edtUser);
        EdtUser.setCompoundDrawablesWithIntrinsicBounds(R.drawable.user, 0, 0, 0);
        EdtPass=findViewById(R.id.edtPass);
        EdtPass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pass, 0, 0, 0);
        BtnIngresar=findViewById(R.id.btnIngresar);



        Fuente: https://www.iteramos.com/pregunta/65981/como-puedo-anadir-una-imagen-en-edittext


        ChbxRecordarUsuario=findViewById(R.id.chbxRecordarUsuario);
        /*recordar Contrasena*/
        db.GetUser();
        String recordar=db.Recordar;
        if(recordar != null && !recordar.isEmpty()){
            if(db.Recordar.equals("true")){
                EdtUser.setText(db.User);
                EdtPass.setText(db.Pass);
                ChbxRecordarUsuario.setChecked(true);
            }else{
                EdtUser.setText("");
                EdtPass.setText("");
                ChbxRecordarUsuario.setChecked(false);
            }
        }else{
            EdtUser.setText("");
            EdtPass.setText("");
            ChbxRecordarUsuario.setChecked(false);
        }
        /*fin recordar Contrasena*/

    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
       // super.onBackPressed();  // optional depending on your needs
        if(PantallaLogin==0){

        }
        if(PantallaLogin==1){
            EdtUser.setVisibility(View.VISIBLE);
            EdtPass.setVisibility(View.VISIBLE);
            BtnIngresar.setVisibility(View.VISIBLE);
            ChbxRecordarUsuario.setVisibility(View.VISIBLE);
            ImvCCSS.setVisibility(View.VISIBLE);

            /*olvido la contrasena*/
            ImvWarning.setVisibility(View.GONE);
            TxtWarning.setVisibility(View.GONE);
            TxtOlvidoContrasena.setVisibility(View.GONE);
            PantallaLogin=0;
        }
    }


    public void GoToMenu(View view) {
        User=String.valueOf(EdtUser.getText());
        Pass=String.valueOf(EdtPass.getText());

        if(User.equals("gsolano") && Pass.equals("123")  ){
            db.ReinitUser();
            db.SetUser("1",User,Pass,User, String.valueOf(ChbxRecordarUsuario.isChecked()));
            Intent GoLogin = new Intent(context, NotasActivity.class);
            startActivity(GoLogin);
        }else{

            View LoginFail = LayoutInflater.from(context).inflate(R.layout.login_fail, null);
            dialog=new Dialog(context);
            dialog.setContentView(LoginFail);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            Button BtnRegresar = (Button)LoginFail.findViewById(R.id.btnRegresar);
            TextView txtOlvidoPass = (TextView)LoginFail.findViewById(R.id.txtOlvidoPass);

            BtnRegresar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    dialog.cancel();
                }
            });
            txtOlvidoPass.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    dialog.cancel();
                }
            });

        }
    }


}
