package com.ecemsevvalcinar.w1_layout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecemsevvalcinar.w1_layout.databinding.RecyclerRowBinding
import com.ecemsevvalcinar.w1_layout.model.Book
import com.squareup.picasso.Picasso

class BookRecyclerAdapter(val bookList: ArrayList<Book>) : RecyclerView.Adapter<BookRecyclerAdapter.BookHolder>() {
    class BookHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BookHolder(binding)
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.binding.recyclerTextBookAbout.text = bookList.get(position).about
        holder.binding.recyclerTextBookName.text = bookList.get(position).bookName
        Picasso.get().load(bookList.get(position).image).into(holder.binding.recyclerImageView)
        //holder.binding.recyclerImageView.setImageURI(bookList.get(position).imageUri)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }


}