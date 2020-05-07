package com.example.androidportfolio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import kotlinx.android.synthetic.main.activity_create_page.*
import java.io.File
import java.io.FileOutputStream


class CreatePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_page)
        bottomNavBarListenerSetup();
        createPdfButtonListener();

        personalInformation.setOnClickListener{
            personalInformationView();
        }
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
            Log.d("Pdf created ", mFilePath);
            Toast.makeText(this, "Pdf was created successfully", Toast.LENGTH_SHORT).show()
            upload(mFilePath, mFileName);

        }catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }
    }

    private fun createPdfTemplate(mDoc: Document){
        //val name = surname.text.toString();
        val boldFont = Font(Font.FontFamily.TIMES_ROMAN,22f , Font.BOLD)
        val lineSeparator = LineSeparator();

        val paragraph = Paragraph(surname + " " + familyName, boldFont)
        paragraph.alignment = Paragraph.ALIGN_LEFT;

        mDoc.addAuthor(surname + " " + familyName);

        mDoc.add(paragraph);
        mDoc.add(Chunk(lineSeparator));

        mDoc.add(Chunk.NEWLINE);
    }


    private fun upload(mFilePath: String, mFileName: String) {
        val storage = Firebase.storage
        var storageRef = storage.reference
        // File or Blob
        // Create the file metadata
        var metadata = storageMetadata {
            contentType = "application/pdf"
        }
        var file = Uri.fromFile(File(mFilePath));
        var uploadTask = storageRef.child("pdfs/$mFileName.pdf").putFile(file, metadata)
        // Upload file and metadata to the path

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener {
        }.addOnFailureListener {
            Log.d("CreatePage", "Failed to upload: $file" + " "+ mFilePath )
        }.addOnSuccessListener {
            Log.d("CreatePage", "Successfully uploaded pdf: $file" )
        }

    }

    private var surname = "";
    private var familyName = "";
    private var street = "";
    private var city = "";
    private var postalCode = "";


    private fun personalInformationView(){
        val verticalParentView = LinearLayout(this);
        verticalParentView.orientation = LinearLayout.VERTICAL;
        verticalParentView.gravity = Gravity.CENTER;

        val horizontalParentView = LinearLayout(this);
        horizontalParentView.orientation = LinearLayout.HORIZONTAL;
        horizontalParentView.gravity = Gravity.CENTER;

        val alert = AlertDialog.Builder(this)
        alert.setTitle("Personal Information");

        val surnameView = createEditText("Surname", R.id.surname)
        val familyNameView = createEditText("Family name", R.id.familyName)
        val streetView = createEditText("Street name and number", R.id.street)
        val postalCodeView = createEditText("Postal Code", R.id.postalCode)
        val cityView = createEditText("City", R.id.city)

        verticalParentView.addView(surnameView);
        verticalParentView.addView(familyNameView);
        verticalParentView.addView(streetView);
        horizontalParentView.addView(postalCodeView);
        horizontalParentView.addView(cityView);
        verticalParentView.addView(horizontalParentView);


        alert.setView(verticalParentView);
        alert.setPositiveButton("Done"){dialog, which ->
            surname = surnameView.text.toString();
            familyName = familyNameView.text.toString();
            street = streetView.text.toString();
            city = cityView.text.toString();
            postalCode = postalCodeView.text.toString();
        }
        alert.show()
    }

    private fun createEditText(hint: String, id: Int): EditText {
        val view = EditText(this)
        view.hint = hint
        view.id = id
        return view;
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
