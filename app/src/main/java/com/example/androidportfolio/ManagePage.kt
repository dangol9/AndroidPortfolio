package com.example.androidportfolio

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_page.*

import kotlinx.android.synthetic.main.activity_manage_page.*
import kotlinx.android.synthetic.main.activity_manage_page.bottom_navigation

class ManagePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_page)
        bottomNavBarListenerSetup();

    }
    private fun bottomNavBarListenerSetup(){

        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){//like switch statement
                R.id.action_create -> {
                    Toast.makeText(this, "Create", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CreatePage::class.java);
                    startActivity(intent);
                    true
                }
                R.id.action_manage ->{
                    Toast.makeText(this, "Manage", Toast.LENGTH_SHORT).show()
                    true
                }
                else->true
            }
        }
    }
}
