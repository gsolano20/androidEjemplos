package com.gsolano.savepdf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

import static androidx.core.content.FileProvider.getUriForFile;
/*
public class MainActivity extends AppCompatActivity {
    Context context;
    String mCurrentPhotoPath;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        webView=new WebView(MainActivity.this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        context = this;

        SavePdf();
    }


    private void SavePdf() {
        String s = "data:application/octet-stream;base64,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3lno/Du5MGtTJisLmJXZ7abeNisOWaO+G1NWtg+SRDQhRRaiUEe4PVah74e6pATTgttK+1kun8wFTvB4IGjT+2GjnNYGWzlAKzSlwc7tw2z4OX564M7vyuIWN04D2/i7OD88PJDpptFybjvOJ83J/5yVlnwKZW5kc3RyZWFtCmVuZG9iago4IDAgb2JqCjw8L1R5cGUvUGFnZS9NZWRpYUJveFswIDAgNTk1IDg0Ml0vUmVzb3VyY2VzPDwvRm9udDw8L0YxIDIgMCBSL0YyIDMgMCBSL0YzIDQgMCBSPj4vWE9iamVjdDw8L2ltZzAgNSAwIFI+Pj4+L0NvbnRlbnRzIDYgMCBSL1BhcmVudCA3IDAgUj4+CmVuZG9iagoyIDAgb2JqCjw8L1R5cGUvRm9udC9TdWJ0eXBlL1R5cGUxL0Jhc2VGb250L1RpbWVzLUJvbGQvRW5jb2RpbmcvV2luQW5zaUVuY29kaW5nPj4KZW5kb2JqCjMgMCBvYmoKPDwvVHlwZS9Gb250L1N1YnR5cGUvVHlwZTEvQmFzZUZvbnQvVGltZXMtUm9tYW4vRW5jb2RpbmcvV2luQW5zaUVuY29kaW5nPj4KZW5kb2JqCjQgMCBvYmoKPDwvVHlwZS9Gb250L1N1YnR5cGUvVHlwZTEvQmFzZUZvbnQvSGVsdmV0aWNhL0VuY29kaW5nL1dpbkFuc2lFbmNvZGluZz4+CmVuZG9iagoxIDAgb2JqCjw8L1R5cGUvWE9iamVjdC9TdWJ0eXBlL0Zvcm0vUmVzb3VyY2VzPDwvRm9udDw8L0YyIDMgMCBSPj4+Pi9CQm94WzAgMCA1MCA1MF0vRm9ybVR5cGUgMS9NYXRyaXggWzEgMCAwIDEgMCAwXS9MZW5ndGggMzgvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJxzCuHSdzNSsFAISeMyVDAAQggZksulYaAZksXlGsIFAIKqBx0KZW5kc3RyZWFtCmVuZG9iago3IDAgb2JqCjw8L1R5cGUvUGFnZXMvQ291bnQgMS9LaWRzWzggMCBSXT4+CmVuZG9iago5IDAgb2JqCjw8L1R5cGUvQ2F0YWxvZy9QYWdlcyA3IDAgUj4+CmVuZG9iagoxMCAwIG9iago8PC9Qcm9kdWNlcihpVGV4dFNoYXJwkiA1LjUuMTIgqTIwMDAtMjAxNyBpVGV4dCBHcm91cCBOViBcKEFHUEwtdmVyc2lvblwpKS9DcmVhdGlvbkRhdGUoRDoyMDIwMDEwNjE1NTQxNy0wNicwMCcpL01vZERhdGUoRDoyMDIwMDEwNjE1NTQxNy0wNicwMCcpPj4KZW5kb2JqCnhyZWYKMCAxMQowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDIzNTQgMDAwMDAgbiAKMDAwMDAwMjA4NyAwMDAwMCBuIAowMDAwMDAyMTc2IDAwMDAwIG4gCjAwMDAwMDIyNjYgMDAwMDAgbiAKMDAwMDAwMDAxNSAwMDAwMCBuIAowMDAwMDAwNTcwIDAwMDAwIG4gCjAwMDAwMDI1NjQgMDAwMDAgbiAKMDAwMDAwMTkzNCAwMDAwMCBuIAowMDAwMDAyNjE1IDAwMDAwIG4gCjAwMDAwMDI2NjAgMDAwMDAgbiAKdHJhaWxlcgo8PC9TaXplIDExL1Jvb3QgOSAwIFIvSW5mbyAxMCAwIFIvSUQgWzw4NjM2MTkzMWZkODQ1OWIyNzIzYjJmZGQ5NjYwYWIwOD48ODYzNjE5MzFmZDg0NTliMjcyM2IyZmRkOTY2MGFiMDg+XT4+CiVpVGV4dC01LjUuMTIKc3RhcnR4cmVmCjI4MjQKJSVFT0YK";
        StringTokenizer st = new StringTokenizer(s, ",");
        String text = st.nextToken();

        String base64 = st.nextToken();


        if (text.equals("data:application/octet-stream;base64")) {
            //    // StringBuffer ByteCode=(StringBuffer)base64;
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            WebView wv=findViewById(R.id.webview);
            wv.loadData(decodedString.toString(), "application/pdf", "utf-8");
            setContentView(webView);

        }
    }
    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return(false);
        }
    }*/



    public class MainActivity extends AppCompatActivity {
        EditText inputText;
        TextView response;
        Button saveButton,readButton;

        private String filename = "SampleFile.pdf";
        private String filepath = "MyFileStorage";
        File myExternalFile;
        String myData = "";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            inputText = (EditText) findViewById(R.id.myInputText);
            response = (TextView) findViewById(R.id.response);


            saveButton =
                    (Button) findViewById(R.id.saveExternalStorage);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String s = "data:application/octet-stream;base64,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3lno/Du5MGtTJisLmJXZ7abeNisOWaO+G1NWtg+SRDQhRRaiUEe4PVah74e6pATTgttK+1kun8wFTvB4IGjT+2GjnNYGWzlAKzSlwc7tw2z4OX564M7vyuIWN04D2/i7OD88PJDpptFybjvOJ83J/5yVlnwKZW5kc3RyZWFtCmVuZG9iago4IDAgb2JqCjw8L1R5cGUvUGFnZS9NZWRpYUJveFswIDAgNTk1IDg0Ml0vUmVzb3VyY2VzPDwvRm9udDw8L0YxIDIgMCBSL0YyIDMgMCBSL0YzIDQgMCBSPj4vWE9iamVjdDw8L2ltZzAgNSAwIFI+Pj4+L0NvbnRlbnRzIDYgMCBSL1BhcmVudCA3IDAgUj4+CmVuZG9iagoyIDAgb2JqCjw8L1R5cGUvRm9udC9TdWJ0eXBlL1R5cGUxL0Jhc2VGb250L1RpbWVzLUJvbGQvRW5jb2RpbmcvV2luQW5zaUVuY29kaW5nPj4KZW5kb2JqCjMgMCBvYmoKPDwvVHlwZS9Gb250L1N1YnR5cGUvVHlwZTEvQmFzZUZvbnQvVGltZXMtUm9tYW4vRW5jb2RpbmcvV2luQW5zaUVuY29kaW5nPj4KZW5kb2JqCjQgMCBvYmoKPDwvVHlwZS9Gb250L1N1YnR5cGUvVHlwZTEvQmFzZUZvbnQvSGVsdmV0aWNhL0VuY29kaW5nL1dpbkFuc2lFbmNvZGluZz4+CmVuZG9iagoxIDAgb2JqCjw8L1R5cGUvWE9iamVjdC9TdWJ0eXBlL0Zvcm0vUmVzb3VyY2VzPDwvRm9udDw8L0YyIDMgMCBSPj4+Pi9CQm94WzAgMCA1MCA1MF0vRm9ybVR5cGUgMS9NYXRyaXggWzEgMCAwIDEgMCAwXS9MZW5ndGggMzgvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJxzCuHSdzNSsFAISeMyVDAAQggZksulYaAZksXlGsIFAIKqBx0KZW5kc3RyZWFtCmVuZG9iago3IDAgb2JqCjw8L1R5cGUvUGFnZXMvQ291bnQgMS9LaWRzWzggMCBSXT4+CmVuZG9iago5IDAgb2JqCjw8L1R5cGUvQ2F0YWxvZy9QYWdlcyA3IDAgUj4+CmVuZG9iagoxMCAwIG9iago8PC9Qcm9kdWNlcihpVGV4dFNoYXJwkiA1LjUuMTIgqTIwMDAtMjAxNyBpVGV4dCBHcm91cCBOViBcKEFHUEwtdmVyc2lvblwpKS9DcmVhdGlvbkRhdGUoRDoyMDIwMDEwNjE1NTQxNy0wNicwMCcpL01vZERhdGUoRDoyMDIwMDEwNjE1NTQxNy0wNicwMCcpPj4KZW5kb2JqCnhyZWYKMCAxMQowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDIzNTQgMDAwMDAgbiAKMDAwMDAwMjA4NyAwMDAwMCBuIAowMDAwMDAyMTc2IDAwMDAwIG4gCjAwMDAwMDIyNjYgMDAwMDAgbiAKMDAwMDAwMDAxNSAwMDAwMCBuIAowMDAwMDAwNTcwIDAwMDAwIG4gCjAwMDAwMDI1NjQgMDAwMDAgbiAKMDAwMDAwMTkzNCAwMDAwMCBuIAowMDAwMDAyNjE1IDAwMDAwIG4gCjAwMDAwMDI2NjAgMDAwMDAgbiAKdHJhaWxlcgo8PC9TaXplIDExL1Jvb3QgOSAwIFIvSW5mbyAxMCAwIFIvSUQgWzw4NjM2MTkzMWZkODQ1OWIyNzIzYjJmZGQ5NjYwYWIwOD48ODYzNjE5MzFmZDg0NTliMjcyM2IyZmRkOTY2MGFiMDg+XT4+CiVpVGV4dC01LjUuMTIKc3RhcnR4cmVmCjI4MjQKJSVFT0YK";
                        StringTokenizer st = new StringTokenizer(s, ",");
                        String text = st.nextToken();

                        String base64 = st.nextToken();


                        if (text.equals("data:application/octet-stream;base64")) {
                            //    // StringBuffer ByteCode=(StringBuffer)base64;
                            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);



                            FileOutputStream fos = new FileOutputStream(myExternalFile);
                            //fos.write(inputText.getText().toString().getBytes());
                            fos.write(decodedString);
                            fos.close();


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    inputText.setText("");
                    response.setText("SampleFile.txt saved to External Storage...");
                }
            });

            readButton = (Button) findViewById(R.id.getExternalStorage);
            readButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /*try {
                        FileInputStream fis = new FileInputStream(myExternalFile);
                       fis.
                        DataInputStream in = new DataInputStream(fis);
                        BufferedReader br =
                                new BufferedReader(new InputStreamReader(in));
                        String strLine;
                        while ((strLine = br.readLine()) != null) {
                            myData = myData + strLine;
                        }
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                  /*  File pdfFile = new File(getExternalFilesDir(filepath), filename);//File path
                    //File pdfFile = new File("/storage/emulated/0/Android/data/com.gsolano.savepdf/files/MyFileStorage/", filename);//File path

                    File imagePath = new File(MainActivity.this.getFilesDir(), "root");
                    File newFile = new File(imagePath, filename);



                    if (pdfFile.exists()) //Checking if the file exists or not
                    {
                        Uri path = getUriForFile(MainActivity.this, "com.mydomain.fileprovider", newFile);
                        //Uri path = Uri.fromFile(getExternalFilesDir("root"));
                        Intent objIntent = new Intent(Intent.ACTION_VIEW);
                        objIntent.setDataAndType(path, "application/pdf");
                        objIntent.setFlags(Intent. FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(objIntent);//Starting the pdf viewer



                    } else {

                        Toast.makeText(MainActivity.this, "The file not exists! ", Toast.LENGTH_SHORT).show();

                    }*/

                    Intent intent;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        /*File file=new File(filepath);
                        Uri uri = FileProvider.getUriForFile(MainActivity.this, getPackageName() + ".provider", file);
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);*/

                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                       // String imageFileName = "Factura_" + timeStamp + "_";
                        String imageFileName = "Factura_";
                        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                        File image = null;
                        File imagePath = new File(storageDir, "");
                        image = new File(imagePath, filename);

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


                    inputText.setText(myData);
                    response.setText("SampleFile.txt data retrieved from Internal Storage...");
                }
            });

            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                saveButton.setEnabled(false);
            }
            else {

                File imagePath = new File(getExternalFilesDir("Documents"), "");
                File newFile = new File(imagePath, filename);
               myExternalFile = new File(imagePath, filename);
               //myExternalFile = new File(getExternalFilesDir(filepath), filename);
            }


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


    }