package com.example.foodrecipe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.foodrecipe.R
import com.example.foodrecipe.UserAccount
import com.example.foodrecipe.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_register)
        auth=Firebase.auth
        binding.registersubmit.setOnClickListener {
            submitUser()
        }
    }
    private fun submitUser(){
        var email:String=binding.email.text.toString()
        val password:String=binding.Password.text.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    var user: UserAccount = UserAccount.instence!!
                    user.userId= auth.currentUser?.uid
                    user.userName= auth.currentUser?.displayName
                    Log.i("TAGY", "${auth.currentUser?.displayName}")
                    Log.i("TAGY", "${auth.currentUser?.uid}")
                    reload()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }



    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        var i=Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}