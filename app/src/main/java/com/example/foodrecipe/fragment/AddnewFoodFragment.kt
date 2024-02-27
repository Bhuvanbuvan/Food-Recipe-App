package com.example.foodrecipe.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foodrecipe.R
import com.example.foodrecipe.UserAccount
import com.example.foodrecipe.databinding.FragmentAddnewFoodBinding
import com.example.foodrecipe.databinding.FragmentFoodListBinding
import com.example.foodrecipe.model.HotelList
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class AddnewFoodFragment : Fragment() {
    var binding: FragmentAddnewFoodBinding?=null
    val _binding get() = binding!!

    private lateinit var userAccount: UserAccount

    private var auth=Firebase.auth
    private  var firestore=Firebase.firestore
    private lateinit var firebaseStorage:FirebaseStorage

    //ImageUri
    var ImageUri:Uri?=null;
    var returnUrl:String?=null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAddnewFoodBinding.inflate(layoutInflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding?.foodImage?.setOnClickListener {
            var i =Intent(Intent.ACTION_PICK)
            i.type="image/*"
            startActivityForResult(i,100)
        }

        binding?.submitBtn?.setOnClickListener {
            sendit()
        }


    }

    private fun sendit() {
        firebaseStorage=FirebaseStorage.getInstance()
        var foodName= binding?.foodname?.text.toString()
        var hotelName= binding?.hotelName?.text.toString()
        var foodPrice= binding?.foodprice?.text.toString()
        var foodDiscript= binding?.discription?.text.toString()

        ImageUri?.let {
            firebaseStorage.getReference("${auth.currentUser?.uid}").child(foodName).putFile(
                it
            ).addOnSuccessListener {task->
                task.metadata?.reference?.downloadUrl?.addOnSuccessListener  { url->
                    Log.i("TAGY", url.toString())
                    var userid:String= auth.currentUser?.uid.toString()
                    var item=HotelList(userid,hotelName,foodName,foodPrice,url.toString(),foodDiscript)
                    firestore.collection("FoodRecepi").document("${auth.currentUser?.uid}").set(item).addOnCompleteListener {
                        Log.i("TAGY","send it sucessfully")
                    }
                }?.addOnFailureListener {
                    Toast.makeText(context,"Faild",Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 && resultCode==RESULT_OK){
            ImageUri = data?.data
            binding?.foodImage?.setImageURI(ImageUri)
        }
    }
}