package com.example.androidportfolio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_create_page.*
import java.io.FileOutputStream


class CreatePage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_page)
        bottomNavBarListenerSetup();
        createPdfButtonListener();

    }

    private fun createPdfButtonListener(){
        addData.setOnClickListener {
            savePdf();
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

            createPdfTemplate(mDoc);

            mDoc.close()
            Log.d("Path is: ", mFilePath);
            Toast.makeText(this, "Pdf was created successfully: saved to" + mFilePath, Toast.LENGTH_SHORT).show()

        }catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }
    }

    private fun createPdfTemplate(mDoc: Document){
        val name = surname.text.toString();
        val famName = familyName.text.toString();

        mDoc.addAuthor(name + " " + famName);
        mDoc.add(Paragraph("Test" + name + " " + " on tema nimi ja" + famName));
        mDoc.add(Chunk.NEWLINE);
    }

    private fun bottomNavBarListenerSetup(){

        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){//like switch statement
                R.id.action_create -> {
                    true
                }
                R.id.action_manage ->{
                    val intent = Intent(this, ManagePage::class.java);
                    startActivity(intent);
                    true
                }
                else->true
            }
        }
    }


}
