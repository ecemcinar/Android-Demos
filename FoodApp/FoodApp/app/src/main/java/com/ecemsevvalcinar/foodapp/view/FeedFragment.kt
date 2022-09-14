package com.ecemsevvalcinar.foodapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecemsevvalcinar.foodapp.R
import com.ecemsevvalcinar.foodapp.adapter.FoodAdapter
import com.ecemsevvalcinar.foodapp.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private var foodAdapter = FoodAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewmodel ve fragment baglandi
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        foodListRV.layoutManager = LinearLayoutManager(context)
        foodListRV.adapter = foodAdapter

        swipeRefreshLayout.setOnRefreshListener {
            foodListRV.visibility = View.GONE
            foodErrorTV.visibility = View.GONE
            foodLoadingPB.visibility = View.VISIBLE
            viewModel.refreshDataFromAPI()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

        //foodListRV.addLineBetweenRows(context)


        /*
        feedButton1.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToFoodFragment()
            Navigation.findNavController(it).navigate(action)
        }
        //button ile fragmentlari baglamak icin
         */

    }

    private fun observeLiveData(){
        // owner yerine this de yazabilirdik fakat viewLifecycleOwner daha dogru
        viewModel.foods.observe(viewLifecycleOwner){ foodList ->
            //null check
            foodList?.let {
                foodListRV.visibility = View.VISIBLE
                foodAdapter.differ.submitList(it) // recycler view'daki listi updatelemis olduk
                // foodAdapter.updateFoodList(foodList)
            }
        }

        viewModel.foodError.observe(viewLifecycleOwner){ error ->
            error?.let {
                if(it){ // true ise hata mesaji var
                    foodErrorTV.visibility = View.VISIBLE
                    foodListRV.visibility = View.GONE
                }else{
                    foodErrorTV.visibility = View.GONE // gosterme
                }
            }
        }

        viewModel.foodLoading.observe(viewLifecycleOwner){ loading->

            loading?.let {
                if(it){
                    foodLoadingPB.visibility = View.VISIBLE
                    foodListRV.visibility = View.GONE
                    foodErrorTV.visibility = View.GONE
                }else{
                    foodLoadingPB.visibility = View.GONE
                }
            }
        }
    }
}