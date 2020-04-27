package com.example.androidportfolio

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter

import kotlinx.android.synthetic.main.activity_main_page.*
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainPage : AppCompatActivity() {

    private val STORAGE_CODE: Int = 10;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

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
