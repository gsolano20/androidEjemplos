package com.novacompcr.imagecompress;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
/*
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
*/
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity  extends AppCompatActivity {

    private ImageButton camara;
    private ImageView imagen;
    private EditText nombreImagen;
    private Button upload;
    private Uri output;
    private String foto;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        nombreImagen=(EditText)findViewById(R.id.nombreImagen);

        camara=(ImageButton)findViewById(R.id.camara);
        camara.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!nombreImagen.getText().toString().trim().equalsIgnoreCase("")){
                    getCamara();
                }else
                    Toast.makeText(MainActivity.this, "Debe nombrar el archivo primero",
                            Toast.LENGTH_LONG).show();
            }

        });
        imagen=(ImageView)findViewById(R.id.imagen);

        upload=(Button)findViewById(R.id.button1);
        upload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //serverUpdate();
            }

        });

    }


    private void getCamara(){
        foto = Environment.getExternalStorageDirectory() +"/"+nombreImagen.getText().toString().trim()+".jpg";
        foto = Environment.getRootDirectory() +"/"+nombreImagen.getText().toString().trim()+".jpg";
        file=new File(foto);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        output = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        ContentResolver cr = this.getContentResolver();
        Bitmap bit = null;
        try {
            bit = MediaStore.Images.Media.getBitmap(cr, output);

            //orientation
            int rotate = 0;
            try {
                ExifInterface exif = new ExifInterface(
                        file.getAbsolutePath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            bit = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //imagen.setBackgroundResource(0);
        imagen.setImageBitmap(bit);
    }
    /*
        private void uploadFoto(String imag){
            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpPost httppost = new HttpPost("http://192.168.0.11/picarcodigo/upload.php");
            MultipartEntity mpEntity = new MultipartEntity();
            ContentBody foto = new FileBody(file, "image/jpeg");
            mpEntity.addPart("fotoUp", foto);
            httppost.setEntity(mpEntity);
            try {
                httpclient.execute(httppost);
                httpclient.getConnectionManager().shutdown();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        private boolean onInsert(){
            HttpClient httpclient;
            List<NameValuePair> nameValuePairs;
            HttpPost httppost;
            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://192.168.0.11/picarcodigo/insertImagen.php"); // Url del Servidor
            //A�adimos nuestros datos
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("imagen",nombreImagen.getText().toString().trim()+".jpg"));

            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httpclient.execute(httppost);
                return true;
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }
        */
    /*
    private void serverUpdate(){
        if (file.exists())new ServerUpdate().execute();
    }

    class ServerUpdate extends AsyncTask<String,String,String> {

        ProgressDialog pDialog;
        @Override
        protected String doInBackground(String... arg0) {
            uploadFoto(foto);
            if(onInsert())
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(ImageUploadActivity.this, "�xito al subir la imagen",
                                Toast.LENGTH_LONG).show();
                    }
                });
            else
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(ImageUploadActivity.this, "Sin �xito al subir la imagen",
                                Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ImageUploadActivity.this);
            pDialog.setMessage("Actualizando Servidor, espere..." );
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
        }

    }
*/
}
