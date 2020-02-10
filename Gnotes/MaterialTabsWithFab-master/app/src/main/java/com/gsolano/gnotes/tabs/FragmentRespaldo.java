package com.gsolano.gnotes.tabs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.gsolano.gnotes.DataBase.RealmModels.RmNotes;
import com.gsolano.gnotes.DataBase.RealmQuerys.RmQueryNotes;
import com.gsolano.gnotes.Models.JsonNotasDecode;
import com.gsolano.gnotes.Models.NotasModel;
import com.gsolano.gnotes.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class FragmentRespaldo extends Fragment {
//    private TextView textView;
    Button BtnExportData,BtnImportData;
    View view;
    Realm realm;
    ArrayList Notas ;
    JSONArray Array;
    JSONObject objNote,parentNote;
    Context context;
    public FragmentRespaldo() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_respaldo, container, false);

//        setSomething(view);
        realm = Realm.getDefaultInstance();
        ValidadatePermisions();
        binding();
        ClickListener();

        return view;
    }

    private void ClickListener() {
        BtnImportData.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                File yourFile = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "OUTPUT.JSON");
                FileInputStream stream = null;
                String jString = null;
                try {
                    stream = new FileInputStream(yourFile);
                    FileChannel fc = stream.getChannel();
                    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                    /* Instead of using default, pass in a decoder. */
                    jString = Charset.defaultCharset().decode(bb).toString();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Gson gson = new Gson();
                JsonNotasDecode NotasFromJSON = gson.fromJson(jString, JsonNotasDecode.class);

                for(int f=0;f<NotasFromJSON.getBody().size();f++){

                    RmNotes RmNotes = new RmNotes();
                    RmNotes.setTitulo( NotasFromJSON.getBody().get(f).getTitulo());
                    RmNotes.setNota( NotasFromJSON.getBody().get(f).getNota());
                    RmNotes.setUser( NotasFromJSON.getBody().get(f).getUser());
                    RmQueryNotes.add(RmNotes);
                    RmQueryNotes.getAll();

                }

            }
        });
        BtnExportData.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                RealmResults<RmNotes> Notes = realm.where(RmNotes.class).findAll();
                Notas=new ArrayList<NotasModel>();
                parentNote=new JSONObject();
                Array=new JSONArray();
                for(RmNotes Note: Notes){

                    objNote=new JSONObject();
                    try {
                        objNote.put("Titulo",Note.getTitulo());
                        objNote.put("Nota",Note.getNota());
                        objNote.put("User",Note.getUser());
                        Array.put(objNote);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                try {
                    parentNote.put("Body",Array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ValidadatePermisions();
                try {
                    Writer output = null;
                    //File file = new File("storage/sdcard/Download/OUTPUT.JSON");
                    File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "OUTPUT.JSON");
                    output = new BufferedWriter(new FileWriter(file));
                    output.write(parentNote.toString());
                    output.close();
                    Toast.makeText(getContext(),"save success", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void ValidadatePermisions() {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            //saveButton.setEnabled(false);
        }
        ActivityCompat.requestPermissions(getActivity(),  new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
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


    private void binding() {
        BtnExportData=view.findViewById(R.id.btnExportData);
        BtnImportData=view.findViewById(R.id.btnImportData);
    }

    private void setSomething(View view) {
//        textView = view.findViewById(R.id.your_text_view_id);
    }
}
