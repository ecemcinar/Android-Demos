package com.ecemsevvalcinar.w1_layout.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecemsevvalcinar.w1_layout.R
import com.ecemsevvalcinar.w1_layout.adapter.BookOverviewRecyclerAdapter
import com.ecemsevvalcinar.w1_layout.databinding.ActivityManageBooksBinding
import com.ecemsevvalcinar.w1_layout.model.Book
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ManageBooksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageBooksBinding
    private lateinit var bookNameAdapter : BookOverviewRecyclerAdapter

    private lateinit var db : FirebaseFirestore

    private lateinit var bookList : ArrayList<Book>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageBooksBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bookList = ArrayList<Book>()
        db = FirebaseFirestore.getInstance()
        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        bookNameAdapter = BookOverviewRecyclerAdapter(bookList)
        binding.recyclerView.adapter = bookNameAdapter

    }



    private fun getData(){
        // value --> documents
        // en son paylasilan sey en yukarda cikicak
        db.collection("Books").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if(error !=null){ // hata var demek
                Toast.makeText(this@ManageBooksActivity,error.localizedMessage, Toast.LENGTH_LONG).show()
            }
            else{
                if(value != null){ // deger dondurulmus demektir
                    if(!value.isEmpty){ // deger bos degilse

                        val documents = value.documents
                        bookList.clear() // tekrarlamalardan kurtuluruz
                        for(document in documents){
                            // casting yapmaliyiz cunku tipini any vermistik
                            // as? --> nullable casting
                            val bookName = document.get("bookName") as String
                            val about = document.get("about") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val post = Book(bookName,about,downloadUrl)
                            bookList.add(post) // cektigimiz verileri listeye ekledik
                        }
                        bookNameAdapter.notifyDataSetChanged() // veriler kendini guncellerse diye
                    }
                }
            }
        }
    }


}