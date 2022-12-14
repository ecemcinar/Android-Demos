package com.ecemsevvalcinar.w1_layout.view

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecemsevvalcinar.w1_layout.R
import com.ecemsevvalcinar.w1_layout.adapter.BookRecyclerAdapter
import com.ecemsevvalcinar.w1_layout.databinding.ActivityBookBinding
import com.ecemsevvalcinar.w1_layout.model.Book
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookBinding
    private lateinit var bookList: ArrayList<Book>
    private lateinit var bookAdapter: BookRecyclerAdapter

    private lateinit var db : FirebaseFirestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bookList = ArrayList<Book>()

        db = FirebaseFirestore.getInstance()

        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        bookAdapter = BookRecyclerAdapter(bookList)
        binding.recyclerView.adapter = bookAdapter
    }
    private fun getData(){
        // value --> documents
        // en son paylasilan sey en yukarda cikicak
        db.collection("Books").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if(error !=null){ // hata var demek
                Toast.makeText(this@BookActivity,error.localizedMessage, Toast.LENGTH_LONG).show()
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
                        bookAdapter.notifyDataSetChanged() // veriler kendini guncellerse diye
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.manage_book_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.manage_books){
            val intent = Intent(this,ManageBooksActivity::class.java)
            //intent.putParcelableArrayListExtra("booklist",bookList)
            startActivity(intent)
        }
        else if(item.itemId == R.id.suggest_book){
            val intent = Intent(this, ChooserActivity::class.java)

            intent.putParcelableArrayListExtra("booklist",bookList)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }


}