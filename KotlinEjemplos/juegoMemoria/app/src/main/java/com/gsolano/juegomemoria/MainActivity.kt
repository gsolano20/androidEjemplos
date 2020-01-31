package com.gsolano.juegomemoria

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cartas_imagen.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var turno :Int =1
    var puntosJugador1:Int =0
    var puntosJugador2:Int =0

    var primeraCarta :Int=0
    var segundaCarta :Int=0
    var primerClick :Int=0
    var segundoClick :Int=0
    var numeroCarta :Int=1

    var cartas =ArrayList<Int>(listOf(11,12,13,14,15,21,22,23,24,25))

    var imagen11 :Int=0
    var imagen12 :Int=0
    var imagen13 :Int=0
    var imagen14 :Int=0
    var imagen15 :Int=0

    var imagen21 :Int=0
    var imagen22 :Int=0
    var imagen23 :Int=0
    var imagen24 :Int=0
    var imagen25 :Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1.- Set Up Ui
        setUpUi()

        //Cargar las Cartas
        CargarCartas()

        //Varajar las cartas
        Collections.shuffle(cartas)

        //set up de listener
        setUpOnClickListener()
    }

    private fun setUpUi(){
        mainActivityTvJugador1.setTextColor(Color.GREEN)
        mainActivityTvJugador1.setTypeface(null,Typeface.BOLD)

        mainActivityTvJugador2.setTextColor(Color.GRAY)
        mainActivityTvJugador2.setTypeface(null,Typeface.NORMAL)
    }
    private fun CargarCartas(){
        imagen11=R.drawable.ic_bike
        imagen12=R.drawable.ic_boat
        imagen13=R.drawable.ic_car
        imagen14=R.drawable.ic_flight
        imagen15=R.drawable.ic_railway

        imagen21=R.drawable.ic_bike
        imagen22=R.drawable.ic_boat
        imagen23=R.drawable.ic_car
        imagen24=R.drawable.ic_flight
        imagen25=R.drawable.ic_railway

    }

    private fun setUpOnClickListener(){
        im11.setOnClickListener(){ var carta:Int=0;  asignarImagenAlaCarta(im11,carta)  }
        im12.setOnClickListener(){ var carta:Int=1;  asignarImagenAlaCarta(im12,carta)  }
        im13.setOnClickListener(){ var carta:Int=2;  asignarImagenAlaCarta(im13,carta)  }
        im21.setOnClickListener(){ var carta:Int=3;  asignarImagenAlaCarta(im21,carta)  }
        im22.setOnClickListener(){ var carta:Int=4;  asignarImagenAlaCarta(im22,carta)  }
        im23.setOnClickListener(){ var carta:Int=5;  asignarImagenAlaCarta(im23,carta)  }
        im31.setOnClickListener(){ var carta:Int=6;  asignarImagenAlaCarta(im31,carta)  }
        im32.setOnClickListener(){ var carta:Int=7;  asignarImagenAlaCarta(im32,carta)  }
        im33.setOnClickListener(){ var carta:Int=8;  asignarImagenAlaCarta(im33,carta)  }
        im41.setOnClickListener(){ var carta:Int=9;  asignarImagenAlaCarta(im41,carta)  }
    }

    private fun asignarImagenAlaCarta(imagen: ImageView, carta: Int) {
        when(cartas[carta]){
            11->imagen.setImageResource(imagen11)
            12->imagen.setImageResource(imagen12)
            13->imagen.setImageResource(imagen13)
            14->imagen.setImageResource(imagen14)
            15->imagen.setImageResource(imagen15)

            21->imagen.setImageResource(imagen21)
            22->imagen.setImageResource(imagen22)
            23->imagen.setImageResource(imagen23)
            24->imagen.setImageResource(imagen24)
            25->imagen.setImageResource(imagen25)
        }
        if(numeroCarta==1){
            primeraCarta=cartas[carta]
            if(primeraCarta>20){
                primeraCarta=primeraCarta-10
            }
            numeroCarta=2
            primerClick=carta
            imagen.isEnabled=false

        }else if (numeroCarta==2){
            segundaCarta=cartas[carta]
            if(segundaCarta>20){
                segundaCarta=segundaCarta-10
            }
            numeroCarta=1
            segundoClick=carta
            im11.isEnabled=false
            im12.isEnabled=false
            im13.isEnabled=false
            im21.isEnabled=false
            im22.isEnabled=false
            im23.isEnabled=false
            im31.isEnabled=false
            im32.isEnabled=false
            im33.isEnabled=false
            im41.isEnabled=false
            var handler= Handler()
            handler.postDelayed(Runnable {
                comprobarCorrecto()
            },1000)
        }

    }

    private fun comprobarCorrecto() {
        if(primeraCarta==segundaCarta){
            when(primerClick){
                0->im11.visibility=View.INVISIBLE
                1->im12.visibility=View.INVISIBLE
                2->im13.visibility=View.INVISIBLE
                3->im21.visibility=View.INVISIBLE
                4->im22.visibility=View.INVISIBLE
                5->im23.visibility=View.INVISIBLE
                6->im31.visibility=View.INVISIBLE
                7->im32.visibility=View.INVISIBLE
                8->im33.visibility=View.INVISIBLE
                9->im41.visibility=View.INVISIBLE
            }
            when(segundoClick){
                0->im11.visibility=View.INVISIBLE
                1->im12.visibility=View.INVISIBLE
                2->im13.visibility=View.INVISIBLE
                3->im21.visibility=View.INVISIBLE
                4->im22.visibility=View.INVISIBLE
                5->im23.visibility=View.INVISIBLE
                6->im31.visibility=View.INVISIBLE
                7->im32.visibility=View.INVISIBLE
                8->im33.visibility=View.INVISIBLE
                9->im41.visibility=View.INVISIBLE
            }
            if(turno==1){
                puntosJugador1++
                mainActivityTvJugador1.setText("Jugador1: "+puntosJugador1)
            }else{
                puntosJugador2++
                mainActivityTvJugador2.setText("Jugador2: "+puntosJugador2)
            }
        }else{
            im11.setImageResource(R.drawable.ic_box_black)
            im12.setImageResource(R.drawable.ic_box_black)
            im13.setImageResource(R.drawable.ic_box_black)
            im21.setImageResource(R.drawable.ic_box_black)
            im22.setImageResource(R.drawable.ic_box_black)
            im23.setImageResource(R.drawable.ic_box_black)
            im31.setImageResource(R.drawable.ic_box_black)
            im32.setImageResource(R.drawable.ic_box_black)
            im33.setImageResource(R.drawable.ic_box_black)
            im41.setImageResource(R.drawable.ic_box_black)
            if(turno==1){
                turno=2
                mainActivityTvJugador1.setTextColor(Color.GRAY)
                mainActivityTvJugador1.setTypeface(null,Typeface.NORMAL)

                mainActivityTvJugador2.setTextColor(Color.GREEN)
                mainActivityTvJugador2.setTypeface(null,Typeface.BOLD)
            }else{
                turno=1
                mainActivityTvJugador1.setTextColor(Color.GREEN)
                mainActivityTvJugador1.setTypeface(null,Typeface.BOLD)

                mainActivityTvJugador2.setTextColor(Color.GRAY)
                mainActivityTvJugador2.setTypeface(null,Typeface.NORMAL)
            }
        }
        im11.isEnabled=true
        im12.isEnabled=true
        im13.isEnabled=true
        im21.isEnabled=true
        im22.isEnabled=true
        im23.isEnabled=true
        im31.isEnabled=true
        im32.isEnabled=true
        im33.isEnabled=true
        im41.isEnabled=true

        finDePartida()
    }

    private fun finDePartida() {
        if(im11.visibility==View.INVISIBLE &&
            im12.visibility==View.INVISIBLE &&
            im13.visibility==View.INVISIBLE &&
            im21.visibility==View.INVISIBLE &&
            im22.visibility==View.INVISIBLE &&
            im23.visibility==View.INVISIBLE &&
            im31.visibility==View.INVISIBLE &&
            im32.visibility==View.INVISIBLE &&
            im33.visibility==View.INVISIBLE &&
            im41.visibility==View.INVISIBLE ){
            var alertDialog=AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setTitle("Fin de la partida")
            alertDialog.setMessage("Jugador1: "+puntosJugador1 + "\nJugador2: "+puntosJugador2)
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK"){
                dialogInterface,i->
                var intent= Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            alertDialog.show()

        }
    }


}
