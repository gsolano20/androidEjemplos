package com.gsolano.gnotes.DataBase.RealmQuerys;

import android.util.Log;

import com.gsolano.gnotes.DataBase.RealmModels.RmNotes;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RmQueryNotes {

        private static int calculateIndex(){
            Realm realm = Realm.getDefaultInstance();
            Number currentIdNum = realm.where(RmNotes.class).max("id");
            int nextId;
            if(currentIdNum == null){
                nextId = 0;
            }else {
                nextId = currentIdNum.intValue()+1;
            }
            return nextId;
        }

        public static void add(final RmNotes Note){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm){
                    int index = calculateIndex();
                    RmNotes realmNote = realm.createObject(RmNotes.class, index);
                    realmNote.setTitulo(Note.getTitulo());
                    realmNote.setNota(Note.getNota());
                    realmNote.setUser(Note.getUser());
                }
            });
        }
        public static void update(final RmNotes Note){


            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm){
                    RmNotes realmNote = realm.where(RmNotes.class)
                            .equalTo("id", Note.getId())
                            .findFirst();

                    realmNote.setTitulo(Note.getTitulo());
                    realmNote.setNota(Note.getNota());
                    realmNote.setUser(Note.getUser());
                }
            });
        }

        public static List<RmNotes> getAll(){
            Realm realm = Realm.getDefaultInstance();
            RealmResults<RmNotes> Notes = realm.where(RmNotes.class).findAll();
            for(RmNotes Note: Notes){
                Log.d("TAG", "id: " + Note.getId() + " Idsolicitud: " + Note.getTitulo() + " DesReparacion: " + Note.getNota());
               
            }
            return Notes;
        }

        public static void DeleteById(int id){
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RmNotes NoteDelete = realm.where(RmNotes.class).equalTo("id", id).findFirst();
            NoteDelete.deleteFromRealm();
            realm.commitTransaction();
        }
    }

