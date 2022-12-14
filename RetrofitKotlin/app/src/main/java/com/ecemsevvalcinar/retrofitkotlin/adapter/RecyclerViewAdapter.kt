package com.ecemsevvalcinar.retrofitkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecemsevvalcinar.retrofitkotlin.R
import com.ecemsevvalcinar.retrofitkotlin.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerViewAdapter(private val cryptoList: ArrayList<CryptoModel>, private val listener: Listener): RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

     var colors: Array<String> = arrayOf("#bd80ff","#dd88ff","#ea9999","#9fc5e8","#b6d7a8","#6fa8dc","#f9cb9c","#b4a7d6")
     interface Listener{
         fun onItemClick(cryptoModel: CryptoModel)
     }

    class RowHolder(view:View): RecyclerView.ViewHolder(view) {

        fun bind(cryptoModel: CryptoModel,colors: Array<String>, position: Int,listener: Listener){
            itemView.setOnClickListener{
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position%8]))
            itemView.text_name.text = cryptoModel.currency
            itemView.text_price.text = cryptoModel.price

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) { // hangi item ne verisi gosterecek onu yaziyoruz
        holder.bind(cryptoList[position],colors,position,listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }


}