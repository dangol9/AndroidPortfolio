package com.example.androidportfolio

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_create_page.*
import java.io.FileOutputStream


class CreatePage : AppCompatActivity() {

    private val STORAGE_CODE: Int = 10;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_page)
        bottomNavBarListenerSetup();

        addData.setOnClickListener {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //IF DENIED ASK
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    requestPermissions(permissions, STORAGE_CODE)
                }else{
                    savePdf();
                }
            }else{
                savePdf();
            }
        }
    }

    private fun bottomNavBarListenerSetup(){

        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){//like switch statement
                R.id.action_create -> {
                    Toast.makeText(this, "Create", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_manage ->{
                    Toast.makeText(this, "Manage", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ManagePage::class.java);
                    startActivity(intent);
                    true
                }
                else->true
            }
        }
    }


    private fun savePdf() {
        val mFileName = fileName.text.toString();
        val mDoc = Document();
        val mFilePath = getExternalFilesDir(null).toString() + "/" + mFileName + ".pdf";
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath));
            //open for writing
            mDoc.open();

            val name = surname.text.toString();
            val famName = familyName.text.toString();

            mDoc.addAuthor(name + " " + famName);
            mDoc.add(Paragraph("Test" + name + " " + " on tema nimi ja" + famName));

            mDoc.close()
            Log.d("Path is: ", mFilePath);
            Toast.makeText(this, "Pdf was created successfully: saved to" + mFilePath, Toast.LENGTH_SHORT).show()

        }catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            STORAGE_CODE ->  {
                if(grantResults.size  >  0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePdf()
                }else{
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
