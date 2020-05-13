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
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import dialogViews.*
import kotlinx.android.synthetic.main.activity_create_page.*
import java.io.File
import java.io.FileOutputStream


class CreatePage: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_page)
        topNavBarListenerSetup()
        bottomNavBarListenerSetup()
        createPdfButtonListener()

        personalInformationTextView.setOnClickListener {
            personalInformationView(this)
        }

        jobExperienceTextView.setOnClickListener {
            jobExperienceView(this)
        }

        educationTextView.setOnClickListener {
            educationView(this)
        }

        personalSkillsTextView.setOnClickListener {
            personalSkillsView(this)
        }
    }


    private fun showEmail(): String? {
        // val bundle = intent.extras
        //val email = bundle!!.getString("email")
        // var emailCut = email?.substring(0, email.lastIndexOf("@"));
        return "kaarel"
    }

    fun createPdfButtonListener() {
        addData.setOnClickListener {
            savePdf()
        }
    }

    private fun savePdf() {
        val mFileName = fileName.text.toString();
        val mDoc = Document();
        val mFilePath = getExternalFilesDir(showEmail()).toString() + "/" + mFileName + ".pdf"
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            //open for writing
            mDoc.open()

            createPdfTemplate(mDoc)

            mDoc.close()
            Log.d("Pdf created ", mFilePath)
            Toast.makeText(this, "Pdf was created successfully", Toast.LENGTH_SHORT).show()
            upload(mFilePath, mFileName)

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }
    }

    private fun createPdfTemplate(mDoc: Document) {
        val boldFont = Font(Font.FontFamily.TIMES_ROMAN, 22f, Font.BOLD)
        val boldFont2 = Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.BOLD)
        val lineSeparator = LineSeparator()

        val paragraph = Paragraph("$surname $familyName", boldFont)
        paragraph.alignment = Paragraph.ALIGN_LEFT

        mDoc.addAuthor("$surname $familyName")

        mDoc.add(paragraph)
        mDoc.add(Chunk(lineSeparator))
        mDoc.add(Paragraph("$surname $familyName"))
        mDoc.add(Paragraph("${street}, ${city}, ${postalCode}"))

        mDoc.add(Chunk.NEWLINE);
        mDoc.add(Paragraph("Job experience", boldFont))
        mDoc.add(Chunk(lineSeparator))
        createJobExperienceField(mDoc)

        mDoc.add(Chunk.NEWLINE);
        mDoc.add(Paragraph("Education", boldFont))
        mDoc.add(Chunk(lineSeparator))
        createEducationField(mDoc)

        mDoc.add(Chunk.NEWLINE);
        mDoc.add(Paragraph("Personal Skills", boldFont))
        mDoc.add(Chunk(lineSeparator))
        mDoc.add(Paragraph("Native Language: ${nativeLanguage}"))
        mDoc.add(Paragraph("Foreign Languages: ", boldFont2))
        mDoc.add(Paragraph(listOfForeignLangauges))
        mDoc.add(Paragraph("Communication Skill: ${communicationSkill}"))
        mDoc.add(Paragraph("Leading Skill: ${leadingSkill}"))
        mDoc.add(Paragraph("Job skills:", boldFont2))
        mDoc.add(Paragraph(listOfJobSkills))
        mDoc.add(Paragraph("Digital skills:", boldFont2))
        mDoc.add(Paragraph(listOfDigitalSkills))

    }

    private fun createJobExperienceField(mDoc: Document) {
        val boldFont2 = Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.BOLD)
        val italic = Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.ITALIC)

        for (i in occupation.indices) {
            val startDate =
                jobStartDay[i].selectedItem.toString() + "/" + jobStartMonth[i].selectedItem.toString() + "/" + jobStartYear[i].selectedItem.toString()
            val endDate =
                jobEndDay[i].selectedItem.toString() + "/" + jobEndMonth[i].selectedItem.toString() + "/" + jobEndYear[i].selectedItem.toString()
            val occupation = occupation[i].text.toString();
            val employerName = employerName[i].text.toString()
            val employerCity = employerCity[i].text.toString()
            val employerCountry = employerCountry[i].text.toString()
            val duties = duties[i].text.toString()

            mDoc.add(
                Paragraph(
                    "$occupation $startDate - $endDate, $employerCity, $employerCountry",
                    boldFont2
                )
            )
            mDoc.add(Paragraph(employerName, italic))
            mDoc.add(Paragraph(duties))
        }
    }

    private fun createEducationField(mDoc: Document) {
        val boldFont2 = Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.BOLD)

        for (i in schoolName.indices) {
            val startDate =
                eduStartDay[i].selectedItem.toString() + "/" + eduStartMonth[i].selectedItem.toString() + "/" + eduStartYear[i].selectedItem.toString()
            val endDate =
                eduEndDay[i].selectedItem.toString() + "/" + eduEndMonth[i].selectedItem.toString() + "/" + eduEndYear[i].selectedItem.toString()
            val schoolName = schoolName[i].text.toString()
            val city = schoolCity[i].text.toString()
            val country = schoolCountry[i].text.toString()

            mDoc.add(Paragraph("$schoolName $startDate - $endDate, $city, $country", boldFont2))
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
        var file =
            Uri.fromFile(File(getExternalFilesDir(showEmail()).toString() + "/" + mFileName + ".pdf"))
        var uploadTask =
            storageRef.child("pdfs/${showEmail()}/$mFileName.pdf").putFile(file, metadata)
        // Upload file and metadata to the path

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener {
        }.addOnFailureListener {
            Log.d("CreatePage", "Failed to upload: $file $mFilePath")
            Toast.makeText(this, "PDF failed!", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Log.d("CreatePage", "Successfully uploaded pdf: $file")
        }

    }

    private fun bottomNavBarListenerSetup() {

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {//like switch statement
                R.id.action_create -> {
                    true
                }
                R.id.action_manage -> {
                    val bundle = intent.extras
                    //val email = bundle!!.getString("email")
                    val intent = Intent(this, ManagePage::class.java)
                    //intent.putExtra("email", email)
                    startActivity(intent);
                    true
                }
                else -> true
            }
        }
    }

    private fun topNavBarListenerSetup() {
        top_navigation.setOnMenuItemClickListener {
            when (it.itemId) {//like switch statement
                R.id.action_logout-> {
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                    true
                }
                else->true
                }
            }
        }
}

