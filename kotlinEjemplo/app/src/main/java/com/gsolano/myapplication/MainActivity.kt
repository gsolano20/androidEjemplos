package com.gsolano.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var edad:Int=0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt_
        btn_.setOnClickListener {
            edad=edt_.text.toString().toInt()
            if(edad>=18){
                //TODO-> Dar acceso a la aplicacion

            }else{
                //TODO-> No Dar acceso a la aplicacion
            }

        }

        //val SO =SistemaOperativo.create("Android")
        //val SO2 =SistemaOperativo.create("IOS")
        //Log.d("TAG",SO.sistemaoperativo)
        //Log.d("TAG",SO.sistemaoperativo)
        //Log.d("TAG","${SistemaOperativo.count}")
    }

    //fun mybtnonclick(view: View) {}
}
