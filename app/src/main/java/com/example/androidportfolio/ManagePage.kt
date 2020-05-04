package com.example.androidportfolio

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_manage_page.*
import java.io.File


class ManagePage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_page)
        val path = getExternalFilesDir(null).toString() + "/";

        bottomNavBarListenerSetup();
        listFilesInDirectory(path);

        //essential if sdk >= 24
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()

    }

    //hiljem proovida listina
    private fun listFilesInDirectory(path: String){
        Log.d("Files", "Path: " + path);
        val files = File(path).listFiles()
        Log.d("Files", "Size: " + files.size)
        val intent = Intent(Intent.ACTION_VIEW);
        for (i in files.indices) {
            Log.d("Files", "FileName:" + files[i].name)
            buildView(files[i], path);

        }

    }

    private fun buildView(file: File, path: String){
        val parentLayout = LinearLayout(this);
        parentLayout.orientation = LinearLayout.HORIZONTAL;
        parentLayout.gravity = Gravity.CENTER;
        val textViewLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        textViewLayoutParams.setMargins(25, 20, 25, 10);

        val fileNameView = TextView(this);
        fileNameView.textSize = 20f;
        fileNameView.text = file.name;
        fileNameView.setOnClickListener(View.OnClickListener {
            intent.setDataAndType(Uri.fromFile(File(path + file.name.toString())), "application/pdf")
            startActivity(intent);
        });

        val fileEditIconView = TextView(this);
        fileEditIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit, 0,0,0);
        fileEditIconView.setTextColor(Color.BLACK);
        fileEditIconView.setOnClickListener{
            Toast.makeText(this,"Edit", Toast.LENGTH_SHORT).show();
        }

        val fileRemoveIconView = TextView(this);
        fileRemoveIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove, 0,0,0);
        fileRemoveIconView.setTextColor(Color.BLACK);
        fileRemoveIconView.setOnClickListener{
            Toast.makeText(this,"Remove", Toast.LENGTH_SHORT).show();
            file.delete();
            finish();
            startActivity(getIntent());
        }

        parentLayout.addView(fileNameView, textViewLayoutParams)
        parentLayout.addView(fileEditIconView, textViewLayoutParams);
        parentLayout.addView(fileRemoveIconView, textViewLayoutParams);
        manageLayout.addView(parentLayout);
    }


    private fun bottomNavBarListenerSetup(){
        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){//like switch statement
                R.id.action_create -> {
                    val intent = Intent(this, CreatePage::class.java);
                    startActivity(intent);
                    true
                }
                R.id.action_manage ->{
                    true
                }
                else->true
            }
        }
    }

}

