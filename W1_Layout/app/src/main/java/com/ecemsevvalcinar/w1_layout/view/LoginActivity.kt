package com.ecemsevvalcinar.w1_layout.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ecemsevvalcinar.w1_layout.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth : FirebaseAuth
    private var email : String = ""
    private var password : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser //nullable verir
        if(currentUser!=null){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // giris yapti buraya donmesine gerek yok
        }
    }
    fun signInClicked(view: View){
        getEmailAndPassword()

        if(email.isNotEmpty() && email.isNotBlank() && password.isNotEmpty() && password.isNotBlank()){
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish() // giris yapti buraya donmesine gerek yok
            }.addOnFailureListener {
                Toast.makeText(this@LoginActivity,it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        else if((email.isEmpty() || email.isBlank()) && (password.isEmpty() || password.isBlank())){
            Toast.makeText(this, "Enter your email and paassword in correct form!!", Toast.LENGTH_SHORT).show()
        }
        else if(email.isEmpty() || email.isBlank()){
            Toast.makeText(this, "Enter your email!", Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty() || password.isBlank()){
            Toast.makeText(this,"Enter your password!", Toast.LENGTH_SHORT).show()
        }
    }

    fun signUpClicked(view: View){

        getEmailAndPassword()

        if(email.isNotEmpty() && email.isNotBlank() && password.isNotEmpty() && password.isNotBlank()){
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                // success olunca cagrilir
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish() // giris yapti buraya donmesine gerek yok
            }.addOnFailureListener{
                Toast.makeText(this@LoginActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }
        else if((email.isEmpty() || email.isBlank()) && (password.isEmpty() || password.isBlank())){
            Toast.makeText(this, "Enter your email and paassword in correct form!!",Toast.LENGTH_SHORT).show()
        }
        else if(email.isEmpty() || email.isBlank()){
            Toast.makeText(this, "Enter your email!",Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty() || password.isBlank()){
            Toast.makeText(this,"Enter your password!",Toast.LENGTH_SHORT).show()
        }

    }
    fun getEmailAndPassword(){
        this.email = binding.editTextEmail.text.toString()
        this.password = binding.editTextPassword.text.toString()
    }
}