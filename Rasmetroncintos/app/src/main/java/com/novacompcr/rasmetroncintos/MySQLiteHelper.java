package com.novacompcr.rasmetroncintos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "socoDb";
    public String IdUser,User, Pass,UserName,Recordar;
    public String id, IdNotas, Descripcion, Titulo;
    public ArrayList NotasItems;

    public NotasInfo NotasDetail;
    public ArrayList<NotasInfo> NotasInfo;
    private String Response;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    /*User*/
    public void onCreate(SQLiteDatabase sqlitedatabase)    {
        sqlitedatabase.execSQL("CREATE TABLE ScUser ( IdUser TEXT,User TEXT, Pass TEXT,UserName TEXT, Recordar TEXT)");
    }

    public void ReinitUser()    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS ScUser");
        onCreate(sqlitedatabase);
    }
    public void SetUser(String IdUser, String User, String Pass, String UserName, String Recordar)    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("IdUser", IdUser);
        contentvalues.put("User", User);
        contentvalues.put("Pass", Pass);
        contentvalues.put("UserName", UserName);
        contentvalues.put("Recordar", Recordar);
        sqlitedatabase.insert("ScUser", null, contentvalues);
        sqlitedatabase.close();
    }
    public void GetUser()    {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor cursor = sqlitedatabase.query("ScUser", new String[] {
                "IdUser", "User", "Pass", "UserName","Recordar"
        }, null, null, null, null, null, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
            if(cursor.getCount() == 0)
            {
                User = "";
            } else
            {
                User = cursor.getString(1);
                IdUser = cursor.getString(0);
                Pass = cursor.getString(2);
                UserName = cursor.getString(3);
                Recordar = cursor.getString(4);
            }
        }
        sqlitedatabase.close();
    }

    /*Fin User*/

    /*Parametros*/
    public void onCreateParametros(SQLiteDatabase sqlitedatabase)    {
        sqlitedatabase.execSQL("CREATE TABLE Parametros ( Id TEXT,Descripcion TEXT,Parametro TEXT)");
    }
    public void ReinitParametros()    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS Parametros");
        onCreateParametros(sqlitedatabase);
    }
    public void SetParametros(String Id, String Descripcion, String Parametro)    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("Id", Id);
        contentvalues.put("Descripcion", Descripcion);
        contentvalues.put("Parametro", Parametro);
        sqlitedatabase.insert("Parametros", null, contentvalues);
        sqlitedatabase.close();
    }
    public String GetParametros(String Parametro)    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "Select Id,Descripcion,Parametro from Parametros where Descripcion= '"+Parametro+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            Parametro = cursor.getString(2);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
            db.close();
        }
        return Parametro;
    }
    /* Fin Parametros*/



    /*User*/
    public void onCreateNotas(SQLiteDatabase sqlitedatabase)    {
        sqlitedatabase.execSQL("CREATE TABLE Notas ( id TEXT,IdNotas TEXT, Titulo TEXT,Descripcion TEXT)");
    }
    public void ReinitNotas()    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.execSQL("DROP TABLE IF EXISTS Notas");
        onCreateNotas(sqlitedatabase);
    }
    public void SetNotas( String id,String IdNotas, String Titulo, String Descripcion)    {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("id", id);
        contentvalues.put("IdNotas", IdNotas);
        contentvalues.put("Titulo", Titulo);
        contentvalues.put("Descripcion", Descripcion);
        sqlitedatabase.insert("Notas", null, contentvalues);
        sqlitedatabase.close();
    }
    public void  getNotas() {
        NotasItems = new ArrayList();
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor cursor = sqlitedatabase.query("Notas", new String[] {
                "id", "IdNotas", "Titulo", "Descripcion"
        }, null, null, null, null, null, null);
        if(cursor.moveToFirst())
            do
            {
                id = cursor.getString(0);
                IdNotas = cursor.getString(1);
                Titulo = cursor.getString(2);
                Descripcion = cursor.getString(3);

                NotasItems.add(new NotasInfo(id, IdNotas , Titulo, Descripcion    ));
            } while(cursor.moveToNext());
        sqlitedatabase.close();
    }
    public void  getNotasDetalle(String id){
        NotasInfo =new ArrayList<NotasInfo>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "IdNotas", "Titulo", "Descripcion"};
        String where = "id='"+id+"'";

        Cursor cursor = db.query("Notas",columns,where,null,null,null,null,null);


        String IdNotas;
        String Titulo;
        String Descripcion;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(0);
                IdNotas  =cursor.getString(1);
                Titulo   =cursor.getString(2);
                Descripcion   =cursor.getString(3);

                NotasDetail = new NotasInfo(id,IdNotas,Titulo,Descripcion);

            } while(cursor.moveToNext());
        }
        db.close();
    }
    public String GetMaxNota()    {

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {

            String selectQuery = "Select  id from Notas Order By id Desc LIMIT 1";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            Response = cursor.getString(0);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
            db.close();
        }
        return Response;
    }

    /*Fin User*/




}
