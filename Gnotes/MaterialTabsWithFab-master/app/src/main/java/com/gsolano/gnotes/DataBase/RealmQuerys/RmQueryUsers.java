package com.gsolano.gnotes.DataBase.RealmQuerys;

import android.util.Log;

import com.gsolano.gnotes.DataBase.RealmModels.RmUsers;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RmQueryUsers {

        private static int calculateIndex(){
            Realm realm = Realm.getDefaultInstance();
            Number currentIdNum = realm.where(RmUsers.class).max("id");
            int nextId;
            if(currentIdNum == null){
                nextId = 0;
            }else {
                nextId = currentIdNum.intValue()+1;
            }
            return nextId;
        }


        public static void add(final RmUsers User){
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm){
                    int index = calculateIndex();
                    RmUsers realmUser = realm.createObject(RmUsers.class, index);
                    realmUser.setNombre(User.getNombre());
                    realmUser.setCorreo(User.getCorreo());
                    realmUser.setClave(User.getClave());
                }
            });
        }
        public static void update(final RmUsers User){


            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm){
                    RmUsers realmUser = realm.where(RmUsers.class)
                            .equalTo("id", User.getId())
                            .findFirst();

                    realmUser.setNombre(User.getNombre());
                    realmUser.setCorreo(User.getCorreo());
                    realmUser.setClave(User.getClave());
                }
            });
        }


        public static List<RmUsers> getAll(){
            Realm realm = Realm.getDefaultInstance();
            RealmResults<RmUsers> Users = realm.where(RmUsers.class).findAll();
            for(RmUsers User: Users){
                Log.d("TAG", "id: " + User.getId() + " Idsolicitud: " + User.getNombre() + " DesReparacion: " + User.getCorreo());
               
            }
            return Users;
        }

        public static RmUsers getUser(final RmUsers User){
            Realm realm = Realm.getDefaultInstance();
            RmUsers realmUser = realm.where(RmUsers.class).equalTo("Correo", User.getCorreo()).findFirst();

            return realmUser;
        }

        public static void DeleteById(int id){
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RmUsers UserDelete = realm.where(RmUsers.class).equalTo("id", id).findFirst();
            UserDelete.deleteFromRealm();
            realm.commitTransaction();
        }
    }

