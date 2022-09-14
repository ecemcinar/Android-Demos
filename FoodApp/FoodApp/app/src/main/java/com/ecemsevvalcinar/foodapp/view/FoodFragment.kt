package com.ecemsevvalcinar.foodapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ecemsevvalcinar.foodapp.R
import com.ecemsevvalcinar.foodapp.databinding.FragmentFoodBinding
import com.ecemsevvalcinar.foodapp.viewmodel.FoodViewModel

class FoodFragment : Fragment() {

    private var foodUuid = 0
    private lateinit var viewModel: FoodViewModel
    private lateinit var dataBinding: FragmentFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_food,container,false)
        return dataBinding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            foodUuid = FoodFragmentArgs.fromBundle(it).foodUuid
        }

        viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java) // baglandi
        viewModel.getDataFromRoom(foodUuid)


        observeLiveData()

        // spinner icin burasi

    }

    private fun observeLiveData(){

        viewModel.foodLiveData.observe(viewLifecycleOwner) { food ->
            food?.let {
                dataBinding.selectedFood = it
                dataBinding.spinnerArray = viewModel
            }
            /*
            //Data binding olmadan
            food?.let {
                foodNameTextF.text = food.countryName
                foodRegionTextV.text = food.countryRegion
                //..........

                // image eklenicek
            }
             */
        }

    }

    private fun observeArray(){
    }
}