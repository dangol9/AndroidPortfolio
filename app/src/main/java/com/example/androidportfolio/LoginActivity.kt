package com.example.androidportfolio

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_login)

        login_button.setOnClickListener{
            performLogIn()
        }


        //change page to register page
        back_to_register.setOnClickListener {
            Log.d("LoginActivity", "try to show register activity")
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

    }
    private fun performLogIn(){
        val email = email_login.text.toString()
        val password = password_login.text.toString()

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }
        //log a login to console
        Log.d("login","Attempted to login with email: $email")

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener //if not successful - return error
                //else if output success to console
                Log.d("Main", "Successfully logged in with user with uid: ${it.result?.user?.uid}" )
                //if successful - go to MainPage
                var intent = Intent(this, MainPage::class.java)
                startActivity(intent)
            }
                //error message if not successful
            .addOnFailureListener{
                Log.d("Main", "Failed to log in user: ${it.message}")
            }
    }
}