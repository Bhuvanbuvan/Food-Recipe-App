package com.example.foodrecipe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.foodrecipe.R
import com.example.foodrecipe.databinding.FragmentFoodListBinding

class FoodListFragment : Fragment() {
    var binding:FragmentFoodListBinding?=null
    val _binding get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentFoodListBinding.inflate(layoutInflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.addfab?.setOnClickListener {
            it.findNavController().navigate(R.id.action_foodListFragment_to_addnewFoodFragment)
        }
    }


}