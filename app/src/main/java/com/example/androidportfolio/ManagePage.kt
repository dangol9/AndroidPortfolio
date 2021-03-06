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
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_manage_page.*
import kotlinx.android.synthetic.main.activity_manage_page.bottom_navigation
import kotlinx.android.synthetic.main.activity_manage_page.top_navigation
import java.io.File


class ManagePage : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_page)
        val path = getExternalFilesDir(showEmail()).toString() + "/"

        topNavBarListenerSetup()
        bottomNavBarListenerSetup()
        listFilesInDirectory(path)


        //essential if sdk >= 24
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()

    }

    private fun showEmail(): String? {
        val bundle = intent.extras
        val email = bundle!!.getString("email")
        var emailCut = email?.substring(0, email.lastIndexOf("@"))
        return emailCut.toString()
    }

    //hiljem proovida listina
    private fun listFilesInDirectory(path: String){
        val storage = Firebase.storage
        val listRef = storage.reference.child("pdfs/${showEmail()}/")


        listRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    val file = File(path + item.name)
                    if(file.exists()){
                        buildListOfFilesView(item, path)
                    }else{
                        val fileRef = storage.reference.child("pdfs/${showEmail()}/" + item.name)
                        fileRef.getFile(file).addOnSuccessListener {
                            buildListOfFilesView(item, path)
                            Log.d("File creation", "Successful")
                        }.addOnFailureListener{
                            Log.d("File creation", "Unsuccessful")
                        }
                    }
                }
            }
            .addOnFailureListener {
                Log.d("Managepage", "Failed to fetch" )
            }

    }


    private fun buildListOfFilesView(storageFile: StorageReference, path: String){
        val parentLayout = LinearLayout(this)
        parentLayout.orientation = LinearLayout.HORIZONTAL
        parentLayout.gravity = Gravity.CENTER

        val textViewLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        textViewLayoutParams.setMargins(25, 20, 25, 10)

        val fileNameView = TextView(this)
        fileNameView.textSize = 20f
        fileNameView.text = storageFile.name
        fileNameView.setOnClickListener(View.OnClickListener {
            //opening the file internally
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.fromFile(File(path + storageFile.name)))
            startActivity(intent)
        })

        val fileEditIconView = TextView(this)
        fileEditIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit, 0,0,0)
        fileEditIconView.setTextColor(Color.BLACK)
        fileEditIconView.setOnClickListener{
            Toast.makeText(this,"Edit", Toast.LENGTH_SHORT).show()
        }

        val fileRemoveIconView = TextView(this)
        fileRemoveIconView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove, 0,0,0)
        fileRemoveIconView.setTextColor(Color.BLACK)

        removeIconListener(fileRemoveIconView, storageFile, path)

        parentLayout.addView(fileNameView, textViewLayoutParams)
        parentLayout.addView(fileEditIconView, textViewLayoutParams)
        parentLayout.addView(fileRemoveIconView, textViewLayoutParams)
        //adding linearlayouts onto managelayout
        manageLayout.addView(parentLayout)
    }

    private fun removeIconListener(view : View, storageFile: StorageReference, path: String){
        val storage = Firebase.storage
        val fileRef = storage.reference.child("pdfs/${showEmail()}/${storageFile.name}")
        view.setOnClickListener{
            Toast.makeText(this,"Remove", Toast.LENGTH_SHORT).show()
            val file = File(path + storageFile.name)
            file.delete()
            fileRef.delete().addOnSuccessListener {
                Log.d("Managepage", "File: ${storageFile.name} deleted successfuly")
            }.addOnFailureListener {
                Log.d("Managepage", "File: ${storageFile.name} deleted unsuccessfuly")
            }
            //refresh page
            finish()
            startActivity(getIntent())
        }
    }

    private fun topNavBarListenerSetup() {
        top_navigation.setOnMenuItemClickListener {
            when (it.itemId) {//like switch statement
                R.id.action_logout-> {
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                    logOut()
                    true
                }
                else->true
            }
        }
    }

    private fun logOut(){
        FirebaseAuth.getInstance().signOut();
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun bottomNavBarListenerSetup(){
        bottom_navigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){//like switch statement
                R.id.action_create -> {
                    val bundle = intent.extras
                    val email = bundle!!.getString("email")
                    val intent = Intent(this, CreatePage::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
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
