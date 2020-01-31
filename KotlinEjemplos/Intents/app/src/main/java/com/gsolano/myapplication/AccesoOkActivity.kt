package com.gsolano.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_acceso_ok.*

class AccesoOkActivity : AppCompatActivity() {
    var edad:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceso_ok)
        edad=intent.getIntExtra(Constant.Edad,0)
        accesoOkActivityTvEdad.setText(edad.toString())
    }
}
