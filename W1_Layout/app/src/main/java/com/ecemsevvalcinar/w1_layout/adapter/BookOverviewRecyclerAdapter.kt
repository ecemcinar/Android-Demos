package com.ecemsevvalcinar.w1_layout.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ecemsevvalcinar.w1_layout.databinding.BookOverviewRowBinding
import com.ecemsevvalcinar.w1_layout.model.Book
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.book_overview_row.view.*

class BookOverviewRecyclerAdapter(val booklist: ArrayList<Book>) : RecyclerView.Adapter<BookOverviewRecyclerAdapter.BookNameHolder>() {


    class BookNameHolder(val binding: BookOverviewRowBinding): RecyclerView.ViewHolder(binding.root),
        View.OnLongClickListener {

        init {
            itemView.setOnLongClickListener(this)
        }

        //recycler view'da item uzerine uzun tiklama
        override fun onLongClick(view: View): Boolean {
            val firestore = FirebaseFirestore.getInstance()
            if(view !=null){
                //Toast.makeText(view.context,"long click",Toast.LENGTH_SHORT).show()
                firestore.collection("Books").whereEqualTo("bookName",view.recyclerRowOverviewBookName.text.toString())
                    .get()
                    .addOnSuccessListener {
                        for(doc in it){
                            firestore.collection("Books").document(doc.id).delete().addOnSuccessListener {
                                Toast.makeText(view.context,"Deleted succes",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(view.context,it.localizedMessage,Toast.LENGTH_LONG).show()
                    }

            }
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookNameHolder {
        val binding = BookOverviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookNameHolder(binding)
    }

    override fun onBindViewHolder(holder: BookNameHolder, position: Int) {
        holder.binding.recyclerRowOverviewBookName.text = booklist.get(position).bookName
    }

    override fun getItemCount(): Int {
       return booklist.size
    }

}