package com.ecemsevvalcinar.w1_layout.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ecemsevvalcinar.w1_layout.databinding.ActivityChooserBinding
import com.ecemsevvalcinar.w1_layout.model.Book

class ChooserActivity : AppCompatActivity() {

    private lateinit var bookList: ArrayList<Book>
    private lateinit var book: Book

    private lateinit var binding : ActivityChooserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bookList = ArrayList<Book>()


        val intent = intent
        bookList = intent.getParcelableArrayListExtra<Book>("booklist") as ArrayList<Book> /* = java.util.ArrayList<com.ecemsevvalcinar.w1_layout.model.Book> */
    }


    fun randomChooser(view: View){
        val number = (0..bookList.size-1).random()
        binding.chosen.text = number.toString()
        book = bookList.get(number)
        //binding.imageViewChosenBook.setImageResource(book.image)
    }
}