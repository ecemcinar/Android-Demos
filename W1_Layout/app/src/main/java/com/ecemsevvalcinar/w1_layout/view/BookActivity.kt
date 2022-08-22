package com.ecemsevvalcinar.w1_layout.view

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecemsevvalcinar.w1_layout.R
import com.ecemsevvalcinar.w1_layout.adapter.BookRecyclerAdapter
import com.ecemsevvalcinar.w1_layout.databinding.ActivityBookBinding
import com.ecemsevvalcinar.w1_layout.model.Book

class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookAdapter: BookRecyclerAdapter
    private lateinit var about: String

    private lateinit var database : SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bookList = ArrayList<Book>()

        val database = this.openOrCreateDatabase("Books", MODE_PRIVATE,null)
        try {


        }catch (e: Exception){
            e.printStackTrace()
        }



        about = "The year is 1984 and the city is Tokyo. \n\nA young woman named Aomame follows a taxi driver’s enigmatic suggestion and begins to notice puzzling discrepancies in the world around her. She has entered, she realizes, a parallel existence, which she calls 1Q84 “Q is for ‘question mark.’ A world that bears a question.” Meanwhile, an aspiring writer named Tengo takes on a suspect ghostwriting project. He becomes so wrapped up with the work and its unusual author that, soon, his previously placid life begins to come unraveled."
        val book = Book("1Q84",about,R.drawable.murakami_1q84)
        bookList.add(book)
        about = "Kafka on the Shore is powered by two remarkable characters: a teenage boy, Kafka Tamura, who runs away from home either to escape a gruesome oedipal prophecy or to search for his long-missing mother and sister; and an aging simpleton called Nakata, who never recovered from a wartime affliction and now is drawn toward Kafka for reasons that, like the most basic activities of daily life, he cannot fathom.\n\nAs their paths converge, and the reasons for that convergence become clear, Haruki Murakami enfolds readers in a world where cats talk, fish fall from the sky, and spirits slip out of their bodies to make love or commit murder. Kafka on the Shore displays one of the world’s great storytellers at the peak of his powers."
        val book2 = Book("Kafka On The Shore",about,R.drawable.murakami_kafka)
        bookList.add(book2)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        bookAdapter = BookRecyclerAdapter(bookList)
        binding.recyclerView.adapter = bookAdapter
    }


    fun randomChooserPage(view: View){
        val intent = Intent(this, ChooserActivity::class.java)

        intent.putParcelableArrayListExtra("booklist",bookList)
        //val book = Book("AA","BB",R.drawable.murakami_1q84)
        //intent.putExtra("bookList",bookList)
        //intent.putExtra("book",book)
        startActivity(intent)
    }
}