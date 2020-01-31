package com.gsolano.retrofit2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gsolano.retrofit2.Tiempo.TiempoActivity
import com.gsolano.retrofit2.futbol.FutbolActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BtnApiTiempo.setOnClickListener(){
            var intent=Intent(this, TiempoActivity::class.java)
            startActivity(intent)
        }


        BtnApiFutbol.setOnClickListener({
            var intent = Intent(this, FutbolActivity::class.java)
            startActivity(intent)
        })

    }
}
