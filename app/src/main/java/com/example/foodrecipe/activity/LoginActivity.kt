package com.example.foodrecipe.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.foodrecipe.R
import com.example.foodrecipe.UserAccount
import com.example.foodrecipe.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth=Firebase.auth
        binding.loginsubmit.setOnClickListener {
            login()
        }

        binding.registerlink.setOnClickListener{
            var intent=Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        var email:String=binding.email.text.toString()
        var password:String=binding.Password.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                   var userAccount: UserAccount = UserAccount.instence!!
                    userAccount.userId= auth.currentUser?.uid
                    userAccount.userName= auth.currentUser?.displayName
                    Log.i("TAGY", "${auth.currentUser?.displayName}")
                    Log.i("TAGY", "${auth.currentUser?.uid}")
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }


    override fun onStart() {
        super.onStart()
        var currentUser=auth.currentUser
        if (currentUser!=null){
            val userAccount: UserAccount = UserAccount.instence!!
            userAccount.userId= auth.currentUser?.uid
            userAccount.userName= auth.currentUser?.displayName
            Log.i("TAGY","${auth.currentUser?.uid}")
            reload()
        }
    }

    private fun reload() {
        var intent=Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}