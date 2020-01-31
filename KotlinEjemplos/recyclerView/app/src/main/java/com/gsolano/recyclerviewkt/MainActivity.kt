package com.gsolano.recyclerviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var muro =ArrayList<Muro>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data()
        mainActivityRecyclerview.layoutManager=LinearLayoutManager(this)
        mainActivityRecyclerview.adapter=MuroAdapter(muro);
    }
    fun data(){
        for(i in 0..1000){
            muro.add(Muro(R.drawable.ic_cake,"Feliz Cumpleaños",R.drawable.ic_alberto,"Alberto"))
            muro.add(Muro(R.drawable.ic_bike,"Salgo con la bici",R.drawable.ic_victoria,"Victoria"))
            muro.add(Muro(R.drawable.ic_cafe,"Tomando un cafe con los amigos",R.drawable.ic_marcos,"Marcos"))

            muro.add(Muro(R.drawable.ic_cake,"Feliz Cumpleaños",R.drawable.ic_alberto,"Alberto"))
            muro.add(Muro(R.drawable.ic_bike,"Salgo con la bici",R.drawable.ic_victoria,"Victoria"))
            muro.add(Muro(R.drawable.ic_cafe,"Tomando un cafe con los amigos",R.drawable.ic_marcos,"Marcos"))
        }
    }
}
