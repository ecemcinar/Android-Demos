package com.ecemsevvalcinar.w1_layout.view


import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ecemsevvalcinar.w1_layout.R
import com.ecemsevvalcinar.w1_layout.databinding.ActivityBoxBinding
import kotlin.random.Random


class BoxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoxBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoxBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
    }

    fun changeColorRandomly(view: View){

        val tv1 = findViewById(view.id) as TextView
        tv1.setBackgroundColor(rgb((0..256).random(),(0..256).random(),(0..256).random()))

    }


}