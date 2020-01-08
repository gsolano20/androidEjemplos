package com.novacompcr.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    WebView myWebView;
    ImageView imgReload;
    TextView message_one, message_two;
    Timer timer;
    boolean connectNew, connectOld;
    String WEB_ROOT; // La URL de la web que se quiere mostrar
    int TIME_WAIT_CHECK;// En milisegundos. Es el tiempo que pasará desde el inicio de la App para empezar a comprobar la
    // conexión.
    int TIME_CHECK; // En milisegundos. Cada cuento tiempo revisará la conexión.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciamos constantes
        WEB_ROOT = getResources().getString(R.string.web_root);
        TIME_WAIT_CHECK = Integer.parseInt(getResources().getString(R.string.time_wait_check));
        TIME_CHECK = Integer.parseInt(getResources().getString(R.string.time_check));


        // Se enlazan las views con el código java.
        myWebView = (WebView) findViewById(R.id.webView_map);
        imgReload = (ImageView) findViewById(R.id.imReload);
        message_one = (TextView) findViewById(R.id.tvReloadMess1);
        message_two = (TextView) findViewById(R.id.tvReloadMess2);

        // Se inicia el TimerTask (una tarea en segundo plano que se ejecutará cada cierto tiempo
        // mientras esté activa la aplicación).
        networkConnected nc = new networkConnected();
        // Se inicia un timer necesario para que el TimerTask sepa cada cuanto tiempo se repetirá.
        timer = new Timer();
        timer.scheduleAtFixedRate(nc, TIME_WAIT_CHECK, TIME_CHECK);
        // 1.- Es el TimerTask que se ejecutará.
        // 2.- Es el tiempo que esperará para ejecutarse por primera vez.
        // 3.- Es el tiempo que tardará en repetirse el TimerTack.

        // ¡Imprescindible! Se encarga de guardar el estado de la antigua conexión y de la nueva. Llamaremos a este if ConectionOK, acuerdate.
        if (connectNew) {
            connectOld = false;
        } else {
            connectOld = true;
        }


        // Comprobamos si existe conexión a internet la primera vez que ejecutamos. Si no aplicas este código y no existe conexión cuando ejecutas la app, esta no funcionará correctamente.
        if (isNetworkConnected()) {
            chargeWeb(String.valueOf(Html.fromHtml(WEB_ROOT)));
        }

    }

    // Comprobamos si existe conexión a internet, si no existe se cargaran unas imágenes
    // sustituyendo el WebView
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) { // No existe conexión
            imgReload.setVisibility(View.VISIBLE);
            message_one.setVisibility(View.VISIBLE);
            message_two.setVisibility(View.VISIBLE);
            return false;
        } else { // Existe conexión
            imgReload.setVisibility(View.INVISIBLE);
            message_one.setVisibility(View.INVISIBLE);
            message_two.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    // Se utiliza para cargar la web en la aplicación
    private void chargeWeb(String web) {
        if (myWebView != null) {
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true); // Activamos JavaScript
            myWebView.loadUrl(web); // Cargamos la página web

            // Indicamos que las páginas internas de la web se muestren dentro de la App
            myWebView.setWebViewClient(new WebViewClient());
            // Indicamos que las páginas externas a la web se muestren en un navegador
            myWebView.setWebViewClient(new WebViewClientExternal());
        }
    }

    // Detectamos cuando el usuario pulsa el botón de retroceso
    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    // Tarea repetitiva en segundo plano. Se encarga de comprobar si la conexión se pierde o no.
    private class networkConnected extends TimerTask {
        @Override
        public void run() {
            // En los TimerTask no se puede hacer referencia a las views por lo que utilizamos
            // "runOnUiThread" para poder acceder a las view del thread principal.
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

                                  // Cambiamos la vista de la actividad para mostrar una imagen (si
                                  // se pierde la conexión) o mostrar la web (si la hemos
                                  // recuperado).
                                  //
                                  // Utilizamos dos variables connectNew y connectOld:
                                  //
                                  //    - connectNew: esta variable es la que indicará si existe o
                                  //                  no conexión.
                                  //    - connectOld: esta variable detectará si se ha sufrido algún
                                  //                  cambio en la conexión desde el útlimo cambio.
                                  //
                                  // Cuando se ejecuta la aplicación, el valor de las dos variables
                                  // es null. Cuando se inicia la clase TimerTask, connectNew recibe
                                  // un valor booleano de la conexión y, como puede ser, que el valor
                                  // sea false en la primera ejecución, en el if ConnectionOK le damos a
                                  // connectOld el valor contrario al de connectNew para que entre
                                  // en la primera condición del IF siguiente (el de abajo de este parrafo).
                                  //
                                  // Esta primera condición sirve para detectar que ha habido un
                                  // cambio entre la conexión anterior y la nueva y por lo tanto la
                                  // vista de la actividad tiene que sufrir un cambio.
                                  //
                                  // Como en esta explicación hemos dado por sentado que la connectNew
                                  // seria false y connectOld seria true, entraría en la primera
                                  // condición del IF y luego solo se tendrá que comprobar el valor
                                  // connectNew para saber qué cambio es el que se tiene que
                                  // aplicar. En este caso false, desconectado.
                                  //
                                  // Por último, solo tenemos que guardar en connectOld este cambio
                                  // de conexión, por lo que connectOld se iguala a connectNew y se
                                  // vuelve a ejecutar la comprobación de la conexión, cambiando o
                                  // no el valor de connectNew. Además, como esta es la segunda vez
                                  // que ejecuta el código, ya no pasará por el if ConnectionOK para
                                  // cambiar el  valor booleano de connectOld. A partir de aquí,
                                  // connectOld solo cambiara su valor si connectNew cambia.

                                  if (connectNew != connectOld) {
                                      if (!connectNew) { //Desconectado.
                                          imgReload.setVisibility(View.VISIBLE);
                                          message_one.setVisibility(View.VISIBLE);
                                          message_two.setVisibility(View.VISIBLE);
                                          myWebView.setVisibility(View.INVISIBLE);
                                      } else { //Conectado.
                                          imgReload.setVisibility(View.INVISIBLE);
                                          message_one.setVisibility(View.INVISIBLE);
                                          message_two.setVisibility(View.INVISIBLE);
                                          myWebView.setVisibility(View.VISIBLE);
                                          chargeWeb(WEB_ROOT);
                                      }
                                  }

                                  connectOld = connectNew;
                              }
                          }

            );
        }

    }
    public class WebViewClientExternal extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (Uri.parse(url).getHost().endsWith(view.getResources().getString(R.string.frag_web_root))) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);

            return true;
        }
    }
}
