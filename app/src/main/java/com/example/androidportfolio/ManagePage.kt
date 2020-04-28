package com.example.androidportfolio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_manage_page.*
import java.io.File


class ManagePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_page)
        bottomNavBarListenerSetup();
        listFilesInDirectory();

    }

    //hiljem proovida listina
    private fun listFilesInDirectory(){
        val path = getExternalFilesDir(null).toString() + "/";
        Log.d("Files", "Path: " + path);
        val files = File(path).listFiles()
        Log.d("Files", "Size: " + files.size)
        for (i in files.indices) {
            Log.d("Files", "FileName:" + files[i].name)
            val fileContainer = TextView(this);
            fileContainer.textSize = 30f;
            fileContainer.text = files[i].name;
            manageLayout.addView(fileContainer);

        }

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
