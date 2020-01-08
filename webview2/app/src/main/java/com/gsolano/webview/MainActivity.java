package com.gsolano.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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
import android.view.textclassifier.TextLinks;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

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
    private String filenameExcel = "Factura.xls";
    private String filepath = "Documents";
    File myExternalFile;
    File myExternalFilexsl;
    String myData = "";

    private AlertError AlertError;

    String base64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

                                                /*Uri photoUri = FileProvider.getUriForFile(MainActivity.this, getPackageName() +".provider", image);
                                                intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setData(photoUri);
                                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                startActivity(intent);*/

                                            } else {
                                                intent = new Intent(Intent.ACTION_VIEW);
                                                intent.setDataAndType(Uri.parse(filepath), "application/xls");
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
            super.onPageFinished(view, url);
        }
    }



    @Override
    protected void onDestroy () {
        if (webView != null)
            webView.destroy();
        super.onDestroy();
    }



    }

