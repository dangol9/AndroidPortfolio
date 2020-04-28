package com.example.androidportfolio

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast

import com.example.androidportfolio.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    //private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)


        register_button.setOnClickListener {

            performRegistration()
        }
        //change page to login page
        already_existing_account.setOnClickListener {
            Log.d("RegisterActivity", "try to show login activity")
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

    }

    private fun performRegistration() {
        val email = email_register.text.toString()
        val password = password_register.text.toString()

        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.length < 6){
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return
        }
        //logging the information to console
        Log.d("RegisterActivity", "Email is: $email")
        Log.d("RegisterActivity", "Password is: $password")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener //if not successful - return error
                //else if successful - output to console
                Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid}" )
                Toast.makeText(this, "User created!", Toast.LENGTH_SHORT).show()
            }
                //error message
            .addOnFailureListener{
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
