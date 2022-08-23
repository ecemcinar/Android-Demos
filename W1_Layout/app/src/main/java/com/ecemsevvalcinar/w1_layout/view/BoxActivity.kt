package com.ecemsevvalcinar.w1_layout.view


import android.graphics.Color
import android.graphics.Color.rgb
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        tv1.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        })
    }


}