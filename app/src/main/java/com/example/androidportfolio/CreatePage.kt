package com.example.androidportfolio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_create_page.*
import java.io.File
import java.io.FileOutputStream


class CreatePage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_page)
        bottomNavBarListenerSetup();
        createPdfButtonListener();
        Toast.makeText(this, "Logged in as: " + showEmail(), Toast.LENGTH_SHORT).show()





    }


     private fun showEmail(): String? {
        val bundle = intent.extras
        val email = bundle!!.getString("email")
        var emailCut = email?.substring(0, email.lastIndexOf("@"));
         return emailCut.toString()
    }

    private fun createPdfButtonListener(){
        addData.setOnClickListener {
            savePdf();
        }
    }

    private fun savePdf() {
        val mFileName = fileName.text.toString();
        val mDoc = Document();
        val mFilePath = getExternalFilesDir(showEmail()).toString() + "/" + mFileName + ".pdf";
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath));
            //open for writing
            mDoc.open();

            createPdfTemplate(mDoc);

            mDoc.close()
            Log.d("Path is: ", mFilePath);
            Toast.makeText(this, "Pdf was created successfully: saved to" + mFilePath, Toast.LENGTH_SHORT).show()
            upload(mFilePath, mFileName);

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
                        val bundle = intent.extras
                        val email = bundle!!.getString("email")
                        val intent = Intent(this, ManagePage::class.java);
                        intent.putExtra("email", email)
                        startActivity(intent);
                        true
                    }
                    else->true
                }
            }
    }

    private fun upload(mFilePath: String, mFileName: String) {
        val storage = Firebase.storage
        var storageRef = storage.reference
        // File or Blob
        // Create the file metadata
        var metadata = storageMetadata {
            contentType = "application/pdf"
        }
        var file = Uri.fromFile(File(getExternalFilesDir(showEmail()).toString() + "/" + mFileName + ".pdf"))
        var uploadTask = storageRef.child("pdfs/${showEmail()}/$mFileName.pdf").putFile(file, metadata)
        // Upload file and metadata to the path

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener {
        }.addOnPausedListener {
            println("Upload is paused")
        }.addOnFailureListener {
            Log.d("CreatePage", "Failed to upload: $file $mFilePath")
            Toast.makeText(this, "PDF failed!", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Log.d("CreatePage", "Successfully uploaded pdf: $file" )
            Toast.makeText(this, "PDF uploaded!", Toast.LENGTH_SHORT).show()
        }

    }


}
