package com.gsolano.webview;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    Context  context;
    public WebView webView;
    private ImageView imageConnect;
    private TextView msjCon, msjConnection;
    Timer timer;
    boolean connectNew, connectOld;
    int TIME_WAIT_CHECK;
    int TIME_CHECK;
    String url = "http://facturasff.com";

    private String filename = "Factura.pdf";
    private String filenameExcel = "Factura.xlsx";
    private String filepath = "Documents";
    File myExternalFile;
    File myExternalFilexsl;
    String myData = "";

    private AlertError AlertError;

    String base64;
    RelativeLayout SplashLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SplashLayout = findViewById(R.id.SplashLayout);
        context=this;
        webView = (WebView) findViewById(R.id.webViewUrl);
        imageConnect = (ImageView) findViewById(R.id.imgConexion);
        msjCon = (TextView) findViewById(R.id.tVMsjCon);
        msjConnection = (TextView) findViewById(R.id.tVMsjConnection);



        TIME_WAIT_CHECK = Integer.parseInt(getResources().getString(R.string.time_wait_check));
        TIME_CHECK = Integer.parseInt(getResources().getString(R.string.time_check));

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.ic_logoice);
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);*/

        networkConnected nc = new networkConnected();

        timer = new Timer();
        timer.scheduleAtFixedRate(nc, TIME_WAIT_CHECK, TIME_CHECK);

        if (connectNew) {
            connectOld = false;
        } else {
            connectOld = true;
        }

        if (isNetworkConnected()) {
            chargeWeb(String.valueOf(Html.fromHtml(url)));
        }

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            //saveButton.setEnabled(false);
        }else {

            myExternalFile = new File(getExternalFilesDir(filepath), filename);
            myExternalFilexsl = new File(getExternalFilesDir(filepath), filenameExcel);
        }
        ActivityCompat.requestPermissions(MainActivity.this,  new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);


    }

    //Metodo que impide que el boton atras cierre la aplicacion
    public void onBackPressed(){

        if (webView.canGoBack()){
            webView.goBack();
        } else {
            super.finish();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) { // No existe conexión
            imageConnect.setVisibility(View.VISIBLE);
            msjCon.setVisibility(View.VISIBLE);
            msjConnection.setVisibility(View.VISIBLE);
            return false;
        } else { // Existe conexión
            imageConnect.setVisibility(View.INVISIBLE);
            msjCon.setVisibility(View.INVISIBLE);
            msjConnection.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    // Se utiliza para cargar la web en la aplicación
    @SuppressLint("JavascriptInterface")
    private void chargeWeb(String web) {
        if (webView != null) {

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl(web);
            webView.setWebViewClient(new MyWebViewClient());
            webView.setWebChromeClient(new MyWebChromeClient());



            webView.setDownloadListener(new DownloadListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                    public void onDownloadStart(String url, String userAgent,
                                                String contentDisposition, String mimeType,
                                                long contentLength) {


                        if (url.startsWith("data:")) {

                            String s = url;
                            StringTokenizer st = new StringTokenizer(s, ",");
                            String text = st.nextToken();
                            base64 = st.nextToken();
                            if(text.equals("data:application/octet-stream;base64")){
                                AlertError=  new AlertError(MainActivity.this);
                                AlertError.Show("Confirmacion","¿Desea Descargar el Archivo?");
                                AlertError.btnAceptar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                            FileOutputStream fos = new FileOutputStream(myExternalFile);
                                            fos.write(decodedString);
                                            fos.close();



                                            Intent intent;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                                                File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                                                File imagePath = new File(storageDir, "");
                                                File image = new File(imagePath, filename);

                                                Uri photoUri = FileProvider.getUriForFile(MainActivity.this, getPackageName() +".provider", image);
                                                intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(photoUri);
                                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                startActivity(intent);
                                            } else {
                                                intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setDataAndType(Uri.parse(filepath), "application/pdf");
                                                intent = Intent.createChooser(intent, "Open File");
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        AlertError.AlertErrordialog.cancel();
                                    }
                                });
                                AlertError.btnCancelar.setVisibility(View.VISIBLE);


                            }
                            if(text.equals("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64")){
                                AlertError=  new AlertError(MainActivity.this);
                                AlertError.Show("Confirmacion","¿Desea Descargar el Archivo?");
                                AlertError.btnAceptar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                                            FileOutputStream fos = new FileOutputStream(myExternalFilexsl);
                                            fos.write(decodedString);
                                            fos.close();



                                            Intent intent;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                                                File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                                                File imagePath = new File(storageDir, "");
                                                File image = new File(imagePath, filenameExcel);

                                                Uri photoUri = FileProvider.getUriForFile(MainActivity.this, getPackageName() +".provider", image);

                                                PackageManager packageManager = getPackageManager();
                                                intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.setDataAndType(photoUri, "application/vnd.ms-excel");
                                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                intent = Intent.createChooser(intent, "Open File");
                                                List list = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);

                                                if (list.size() > 0) {
                                                    try {
                                                        startActivity(intent);
                                                    } catch (ActivityNotFoundException e) {
                                                        Toast.makeText(MainActivity.this, "Application not found", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    Toast.makeText(MainActivity.this, "No exiten aplicaciones para abrir el archivo", Toast.LENGTH_SHORT).show();
                                                }
                                               /* intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.setDataAndType(photoUri, "application/vnd.ms-excel");
                                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                try {
                                                    startActivity(intent);
                                                } catch (ActivityNotFoundException e) {
                                                    Toast.makeText(MainActivity.this, "Application not found", Toast.LENGTH_SHORT).show();
                                                }*/

                                            } else {
                                                intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setDataAndType(Uri.parse(filepath), "application/vnd.ms-excel");
                                                intent = Intent.createChooser(intent, "Open File");
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        AlertError.AlertErrordialog.cancel();
                                    }
                                });
                                AlertError.btnCancelar.setVisibility(View.VISIBLE);


                            }

                        }
                        if (url.startsWith("blob:")) {
                            String s = url.replace("blob:","");
                            //try {
                            //    //URLDecoder.decode( s, "UTF-8" );
                            //    //webView.loadUrl(URLDecoder.decode( s, "UTF-8" ));
                            //    //webView.setWebChromeClient(new MyWebChromeClient());
                            //} catch (UnsupportedEncodingException e) {
                            //    e.printStackTrace();
                            //}
                        }
                    }
                });
            }
        return;
    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    private class MyWebChromeClient extends WebChromeClient{

        @Override
        public boolean onJsAlert(WebView view, String url, String message,final JsResult result){
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result){
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,String defaultValue, final JsPromptResult result){
            return true;
        }

    }
    // Tarea repetitiva en segundo plano. Se encarga de comprobar si la conexión se pierde o no.
    private class networkConnected extends TimerTask {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  // Comprobar si existe o no conexión a internet
                                  ConnectivityManager connectivityManager = (ConnectivityManager)
                                          getSystemService(Context.CONNECTIVITY_SERVICE);
                                  NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                                  if (info == null || !info.isConnected() || !info.isAvailable()) {
                                      connectNew = false; // Desconectado
                                  } else {
                                      connectNew = true; // Conectado
                                  }

                                  if (connectNew != connectOld) {
                                      if (!connectNew) { //Desconectado.
                                          imageConnect.setVisibility(View.VISIBLE);
                                          msjCon.setVisibility(View.VISIBLE);
                                          msjConnection.setVisibility(View.VISIBLE);
                                          webView.setVisibility(View.INVISIBLE);
                                      } else { //Conectado.
                                          imageConnect.setVisibility(View.INVISIBLE);
                                          msjCon.setVisibility(View.INVISIBLE);
                                          msjConnection.setVisibility(View.INVISIBLE);
                                          webView.setVisibility(View.VISIBLE);
                                          chargeWeb(url);
                                      }
                                  }

                                  connectOld = connectNew;
                              }
                          }

            );
        }

    }


    public class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //Al dar clic en un link se obligará a cargar dentro del WebView.
            Log.e("url", url);
            view.loadUrl(url);
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            //return
            WebResourceResponse returnResponse = null;
            if ((request).equals("http://facturasff.com/FacturaElectronica/FacturarFE")) {
                Log.i("tag", "shouldInterceptRequest path:" + request.getUrl().getPath());
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            //
            if(url.equals("http://facturasff.com/Agenda/Medico") || url.equals("http://facturasff.com/Account/LogOn?ReturnUrl=%2f")){

                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        injectCSS();
                        SplashLayout.animate()
                        .translationY(0)
                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                SplashLayout.setVisibility(View.GONE);

                            }
                        });
                    }
                },4000);
            }
            super.onPageFinished(view, url);
        }
    }



    @Override
    protected void onDestroy () {
        if (webView != null)
            webView.destroy();
        super.onDestroy();
    }


    private void injectCSS() {
        try {
            InputStream inputStream = getAssets().open("style.css");
            int bufferSize = 1024;
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}

