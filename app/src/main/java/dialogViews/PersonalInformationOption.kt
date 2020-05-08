package dialogViews

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.androidportfolio.R
import utils.createEditTextView
import utils.createHorizontalLayout

var surname = ""
var familyName = ""
var street = ""
var city = ""
var postalCode = ""

fun personalInformationView(context : Context){

    val verticalParentView = LinearLayout(context)
    verticalParentView.orientation = LinearLayout.VERTICAL
    verticalParentView.gravity = Gravity.CENTER

    val horizontalParentView = createHorizontalLayout(context)

    val alert = AlertDialog.Builder(context)
    alert.setTitle("Personal Information")

    val surnameView = createEditTextView(context,R.id.surname,"Surname")
    val familyNameView = createEditTextView(context,R.id.familyName,"Family name")
    val streetView = createEditTextView(context,R.id.street,"Street name and number")
    val postalCodeView = createEditTextView(context,R.id.postalCode,"Postal Code")
    val cityView = createEditTextView(context,R.id.city,"City")

    verticalParentView.addView(surnameView)
    verticalParentView.addView(familyNameView)
    verticalParentView.addView(streetView)
    horizontalParentView.addView(postalCodeView)
    horizontalParentView.addView(cityView)
    verticalParentView.addView(horizontalParentView)


    alert.setView(verticalParentView)
    alert.setPositiveButton("Done"){_, _ ->
        surname = surnameView.text.toString()
        familyName = familyNameView.text.toString()
        street = streetView.text.toString()
        city = cityView.text.toString()
        postalCode = postalCodeView.text.toString()
    }
    alert.show()
}