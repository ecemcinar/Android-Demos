package com.ecemsevvalcinar.foodapp.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.ecemsevvalcinar.foodapp.R
import com.ecemsevvalcinar.foodapp.databinding.FoodRowBinding
import com.ecemsevvalcinar.foodapp.model.Food
import com.ecemsevvalcinar.foodapp.view.FeedFragment
import com.ecemsevvalcinar.foodapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.food_row.view.*

class FoodAdapter: RecyclerView.Adapter<FoodAdapter.FoodViewHolder>(),FoodClickListener{
    class FoodViewHolder(var view: FoodRowBinding) : RecyclerView.ViewHolder(view.root){

    }
    private val differCallback = object: DiffUtil.ItemCallback<Food>(){
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FoodRowBinding>(inflater, R.layout.food_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = differ.currentList[position]

        holder.view.food = food

        // listener da ayarlanacak
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onFoodClicked(view: View) {
        val uuid = view.uuid_textView.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToFoodFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }


}