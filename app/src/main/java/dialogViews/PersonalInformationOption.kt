package dialogViews

import android.content.Context
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.androidportfolio.R

var surname = "";
var familyName = "";
var street = "";
var city = "";
var postalCode = "";

fun personalInformationView(context : Context){

    val verticalParentView = LinearLayout(context);
    verticalParentView.orientation = LinearLayout.VERTICAL;
    verticalParentView.gravity = Gravity.CENTER;

    val horizontalParentView = LinearLayout(context);
    horizontalParentView.orientation = LinearLayout.HORIZONTAL;
    horizontalParentView.gravity = Gravity.CENTER;

    val alert = AlertDialog.Builder(context)
    alert.setTitle("Personal Information");

    val surnameView = EditText(context);
    surnameView.hint = "Surname";
    surnameView.id = R.id.surname;

    val familyNameView = EditText(context);
    familyNameView.hint = "Family name";
    familyNameView.id = R.id.familyName;

    val streetView = EditText(context);
    streetView.hint = "Street name and number";
    streetView.id = R.id.street;

    val postalCodeView = EditText(context);
    postalCodeView.hint = "Postal Code";
    postalCodeView.id = R.id.postalCode;

    val cityView = EditText(context);
    cityView.hint = "City";
    cityView.id = R.id.city;

    verticalParentView.addView(surnameView);
    verticalParentView.addView(familyNameView);
    verticalParentView.addView(streetView);
    horizontalParentView.addView(postalCodeView);
    horizontalParentView.addView(cityView);
    verticalParentView.addView(horizontalParentView);


    alert.setView(verticalParentView);
    alert.setPositiveButton("Done"){_, _ ->
        surname = surnameView.text.toString();
        familyName = familyNameView.text.toString();
        street = streetView.text.toString();
        city = cityView.text.toString();
        postalCode = postalCodeView.text.toString();
    }
    alert.show()
}