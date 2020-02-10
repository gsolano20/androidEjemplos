package com.gsolano.gnotes;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gsolano.gnotes.DataBase.RealmModels.RmUsers;
import com.gsolano.gnotes.DataBase.RealmQuerys.RmQueryUsers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText    _emailText;
     EditText   _passwordText;
    Button      _loginButton;
    TextView    _signupLink;
    String email,password;
    Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        realm = Realm.getDefaultInstance();
        //ButterKnife.inject(this);

        _emailText=findViewById(R.id.input_email);
        _passwordText=findViewById(R.id.input_password);
        _loginButton=findViewById(R.id.btn_login);
        _signupLink=findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
       //String Encrypt= base64(_passwordText.getText().toString());
        validarUsuario();
        //provar();

    }
    public void validarUsuario(){
        RmUsers RmUsers=new RmUsers();
        RmUsers.setCorreo(email);
        RmUsers RequestUser=RmQueryUsers.getUser(RmUsers);

        if(RequestUser != null){
            if(RequestUser.getClave().equals(password)){
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("Correo",email);
                startActivity(intent);
            }else{
                AlertDialog.Builder Dialog = new AlertDialog.Builder(LoginActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                Dialog.setMessage("Usuario o Contraseña Invalida");
                Dialog.show();

            }
        }else{
            AlertDialog.Builder Dialog = new AlertDialog.Builder(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            Dialog.setMessage("Usuario no existe");
            Dialog.show();
        }


    };




private void provar (){
    String[] myArray = {"a", "b", "c", "d", "d", "c", "b", "a"};
    int n=8;
    int size=myArray.length;

    String Simetric="Symmetric";
    int centro =size/2;
    int	centro1 =centro-1;
    int centro2 =centro;
    for(int i=0; i<=n/2; i++){
        if(i<myArray.length/2){
            String text1,texto2;
            text1=myArray[centro1];
            texto2=myArray[centro2];
            if(text1.equals(texto2)){
                Simetric= "Symmetric";
            }else{
                Simetric= "Asymmetric";
            }
            centro1--;
            centro2++;
        }else{
            System.out.println(Simetric);
        }

    }

}



    public static String base64(String base) {
        byte[] data = new byte[0];
        try {
            data = base.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
