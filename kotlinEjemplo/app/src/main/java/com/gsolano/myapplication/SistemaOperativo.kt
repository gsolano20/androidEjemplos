package com.gsolano.myapplication

import android.util.Log;

class SistemaOperativo private constructor(var sistemaoperativo: String) {
    init{
        count++
        Log.d("TAG","Init Sistema Operativo")
    }
    companion object{
        init{
            Log.d("TAG","Init companion object")
        }
        var count :Int =0
        fun create(sistemaOperativo:String):SistemaOperativo=SistemaOperativo(sistemaOperativo)
    }
}