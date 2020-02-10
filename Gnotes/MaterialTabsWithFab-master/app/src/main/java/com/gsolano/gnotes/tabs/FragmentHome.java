package com.gsolano.gnotes.tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gsolano.gnotes.Adapters.AdapterNotas;
import com.gsolano.gnotes.DataBase.RealmModels.RmNotes;
import com.gsolano.gnotes.DataBase.RealmModels.RmUsers;
import com.gsolano.gnotes.DataBase.RealmQuerys.RmQueryNotes;
import com.gsolano.gnotes.MainActivity;
import com.gsolano.gnotes.Models.NotasModel;
import com.gsolano.gnotes.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class FragmentHome extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<NotasModel> Notas ;
    NotasModel objUpdateNotes;
     View view;
    FloatingActionButton floatingActionButton;
    Button SaveActionButton,btnUpdate;
    TextView CloseNewNote;
    Realm realm;
    public String User;

    public FragmentHome(String user) {
        User=user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_home, container, false);
        realm = Realm.getDefaultInstance();
        data();
        SetAdapter();
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
    }
    public void SetAdapter() {
        ListAdapter adapter = new AdapterNotas(getActivity(), Notas);
        ListView NotesView = view.findViewById(R.id.homelistNotes);
        NotesView.setOnItemClickListener(this);
        NotesView.setAdapter( adapter);
        setFab();

    }


    private void data(){

        RealmResults<RmNotes> Notes2 = realm.where(RmNotes.class).findAll();
        RealmResults<RmNotes> Notes = realm.where(RmNotes.class).equalTo("User", User).findAll();
        Notas=new  ArrayList<NotasModel>();
        for(RmNotes Note: Notes){
            Notas.add(new NotasModel(Note.getId(),Note.getTitulo(),Note.getNota(),User));
        }
    }

    private void setFab() {
        floatingActionButton  = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                AddLine();

            }
        });
        SaveActionButton  = view.findViewById(R.id.btnSave);

        SaveActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                SaveLine();
                btnUpdate=view.findViewById(R.id.btnUpdate);
                btnUpdate.setVisibility(View.GONE);
                SaveActionButton.setVisibility(View.VISIBLE);


                EditText TituloNota=view.findViewById(R.id.NoteTitle);
                EditText NotaNota=view.findViewById(R.id.NoteText);
                RmNotes RmNotes = new RmNotes();
                RmNotes.setTitulo(TituloNota.getText().toString());
                RmNotes.setNota(NotaNota.getText().toString());
                RmNotes.setUser(User);
                RmQueryNotes.add(RmNotes);
                RmQueryNotes.getAll();

                TituloNota.setText("");
                NotaNota.setText("");


                data();
                SetAdapter();
            }
        });

        btnUpdate=view.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                SaveLine();


                EditText TituloNota=view.findViewById(R.id.NoteTitle);
                EditText NotaNota=view.findViewById(R.id.NoteText);
                RmNotes RmNotes = new RmNotes();

                RmNotes.setId(objUpdateNotes.getId());
                RmNotes.setTitulo(TituloNota.getText().toString());
                RmNotes.setNota(NotaNota.getText().toString());
                RmNotes.setUser(User);
                RmQueryNotes.update(RmNotes);
                RmQueryNotes.getAll();

                TituloNota.setText("");
                NotaNota.setText("");


                data();
                SetAdapter();
            }
        });

        CloseNewNote  = view.findViewById(R.id.CloseNewNote);

        CloseNewNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                floatingActionButton.setVisibility(View.VISIBLE);
                ListView NotesView = view.findViewById(R.id.homelistNotes);
                RelativeLayout LayoutNewNotes = view.findViewById(R.id.LayoutNewNotes);
                NotesView.setVisibility(View.VISIBLE);
                LayoutNewNotes.setVisibility(View.GONE);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void AddLine() {
        ListView NotesView = view.findViewById(R.id.homelistNotes);
        RelativeLayout LayoutNewNotes = view.findViewById(R.id.LayoutNewNotes);
        NotesView.setVisibility(View.GONE);
        LayoutNewNotes.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.GONE);
    }

    @SuppressLint("RestrictedApi")
    private void SaveLine() {
        floatingActionButton.setVisibility(View.VISIBLE);
        ListView NotesView = view.findViewById(R.id.homelistNotes);
        RelativeLayout LayoutNewNotes = view.findViewById(R.id.LayoutNewNotes);
        NotesView.setVisibility(View.VISIBLE);
        LayoutNewNotes.setVisibility(View.GONE);
    }
    public String isnull(String campo, String resp){
        if(campo ==null || campo.isEmpty() || campo.equals("")){
            return resp;
        }
        return campo;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
        objUpdateNotes = Notas.get(position);

        AddLine();
        SaveActionButton  = view.findViewById(R.id.btnSave);
        btnUpdate=view.findViewById(R.id.btnUpdate);
        btnUpdate.setVisibility(View.VISIBLE);
        SaveActionButton.setVisibility(View.GONE);

        EditText TituloNota=view.findViewById(R.id.NoteTitle);
        EditText NotaNota=view.findViewById(R.id.NoteText);
        TituloNota.setText(isnull(objUpdateNotes.getTitulo(),""));
        NotaNota.setText(isnull(objUpdateNotes.getNota(),""));
    }
}
