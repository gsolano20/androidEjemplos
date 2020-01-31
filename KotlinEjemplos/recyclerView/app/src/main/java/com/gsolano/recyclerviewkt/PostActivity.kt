package com.gsolano.recyclerviewkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        postActivityTextoPost.text=intent.getStringExtra(Constant.POST)
        postActivityFotoPost.setImageResource(intent.getIntExtra(Constant.FOTO,0))
    }
}
