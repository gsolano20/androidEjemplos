package com.gsolano.baseballui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private lateinit var sliderAdapter: Slider_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        sliderAdapter= Slider_Adapter(this)
        introActivityViewpager.adapter=sliderAdapter
        introActivityViewpager.setOnClickListener({
            startActivity(Intent(this@IntroActivity,SplashActivity::class.java))
        })
    }
}
