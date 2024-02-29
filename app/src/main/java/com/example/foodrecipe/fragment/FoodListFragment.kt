package com.example.foodrecipe.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipe.R
import com.example.foodrecipe.activity.LoginActivity
import com.example.foodrecipe.adapter.FoodListAdapter
import com.example.foodrecipe.databinding.FragmentFoodListBinding
import com.example.foodrecipe.model.HotelList
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

class FoodListFragment : Fragment() {
    var binding:FragmentFoodListBinding?=null
    val _binding get() = binding!!
    lateinit var foodadapter:FoodListAdapter


    private var auth= Firebase.auth
    private  var firestore= Firebase.firestore
    private lateinit var firebaseStorage: FirebaseStorage

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
        setUpRecyclerView()
        binding?.addfab?.setOnClickListener {
            it.findNavController().navigate(R.id.action_foodListFragment_to_addnewFoodFragment)
        }
        binding?.signout?.setOnClickListener {
            auth.signOut()
            var i =Intent(activity,LoginActivity::class.java)
            startActivity(i)
        }
    }

    private fun setUpRecyclerView() {
        foodadapter= FoodListAdapter()
        binding?.recyclerView.apply {
            this!!.layoutManager =LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=foodadapter
        }
        firestore.collection("FoodRecepi")
            .whereEqualTo("id", auth.currentUser!!.uid.toString())
            .get().addOnSuccessListener {querysnapshot->
            if (querysnapshot!=null){
                var Foods= mutableListOf<HotelList>()
                for (snapshot in querysnapshot){
                    Foods.add(HotelList(snapshot.data.get("id"),
                        snapshot.data.get("hotelName").toString(),
                        snapshot.data.get("foodName").toString(),
                        snapshot.data.get("foodPrice").toString(),
                        snapshot.data.get("image").toString(),
                        snapshot.data.get("disc").toString(),
                    ))
                }
                foodadapter.differ.submitList(Foods)
            }
        }
    }


}