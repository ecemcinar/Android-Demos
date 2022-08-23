package com.ecemsevvalcinar.w1_layout.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ecemsevvalcinar.w1_layout.R
import com.ecemsevvalcinar.w1_layout.databinding.ActivityBoxBinding
import com.ecemsevvalcinar.w1_layout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuIflater = menuInflater
        menuIflater.inflate(R.menu.book_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.show_books){
            val intent = Intent(this, BookActivity::class.java)
            startActivity(intent)
        }
        else if(item.itemId == R.id.show_boxes){
            val intent = Intent(this, BoxActivity::class.java)
            startActivity(intent)
        }
        else if(item.itemId == R.id.upload){
            val intent = Intent(this,UploadActivity::class.java)
            startActivity(intent)
        }

        //else if(item.itemId == R.id.login){
        //  val intent = Intent(this,LoginActivity::class.java)
        //startActivity(intent)
        //}
        // <item android:id="@+id/login" android:title="Login/Register"></item>

        return super.onOptionsItemSelected(item)
    }


}