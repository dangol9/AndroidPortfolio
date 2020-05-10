package dialogViews

import android.content.Context
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.androidportfolio.R
import java.util.*
import utils.createEditTextView
import utils.createHorizontalLayout
import utils.createSpinnerView

var eduStartDate = ""
var eduEndDate = ""
var schoolName = ""
var schoolCity = ""
var schoolCountry =""

fun educationView(context : Context){
    val alert = AlertDialog.Builder(context)
    alert.setTitle("Education")

    val days = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31)
    val months = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)

    val verticalParentView = LinearLayout(context)
    verticalParentView.orientation = LinearLayout.VERTICAL
    verticalParentView.gravity = Gravity.CENTER

    val fromHorizontalParentView = createHorizontalLayout(context)
    val upToHorizontalParentView = createHorizontalLayout(context)
    val detailsHorizontalParentView = createHorizontalLayout(context)

    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

    val fromTextView = TextView(context)
    fromTextView.text = "From"

    val startDayView = createSpinnerView(context, R.id.eduStartDay)
    val startMonthView = createSpinnerView(context, R.id.eduStartMonth)
    val startYearView = createSpinnerView(context, R.id.eduStartYear)

    val upToTextView = TextView(context)
    upToTextView.text = "Up to"

    val endDayView= createSpinnerView(context, R.id.eduEndDay)
    val endMonthView= createSpinnerView(context, R.id.eduEndMonth)
    val endYearView =createSpinnerView(context, R.id.eduEndYear)

    val dayAdapter = ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, days)
    val monthAdapter = ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, months)
    val yearAdapter =  ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, createYearList())

    startDayView.adapter = dayAdapter
    startMonthView.adapter = monthAdapter
    startYearView.adapter = yearAdapter

    endDayView.adapter = dayAdapter
    endMonthView.adapter = monthAdapter
    endYearView.adapter = yearAdapter

    val schoolNameView = createEditTextView(context, R.id.schoolName, "School name")
    val schoolCityView = createEditTextView(context,  R.id.schoolCity, "City")
    val schoolCountryView = createEditTextView(context,  R.id.schoolCountry, "Country")

    fromHorizontalParentView.addView(fromTextView,layoutParams)
    fromHorizontalParentView.addView(startDayView,layoutParams)
    fromHorizontalParentView.addView(startMonthView,layoutParams)
    fromHorizontalParentView.addView(startYearView,layoutParams)
    verticalParentView.addView(fromHorizontalParentView)

    upToHorizontalParentView.addView(upToTextView,layoutParams)
    upToHorizontalParentView.addView(endDayView, layoutParams)
    upToHorizontalParentView.addView(endMonthView, layoutParams)
    upToHorizontalParentView.addView(endYearView, layoutParams)
    verticalParentView.addView(upToHorizontalParentView)
    verticalParentView.addView(schoolNameView);
    detailsHorizontalParentView.addView(schoolCityView, layoutParams)
    detailsHorizontalParentView.addView(schoolCountryView, layoutParams)
    verticalParentView.addView(detailsHorizontalParentView)

    alert.setView(verticalParentView)

    alert.setPositiveButton("Done"){_, _ ->
        eduStartDate = startDayView.selectedItem.toString() + "/" + startMonthView.selectedItem.toString() + "/" + startYearView.selectedItem.toString()
        eduEndDate = endDayView.selectedItem.toString() + "/" + endMonthView.selectedItem.toString() + "/" + endYearView.selectedItem.toString()
        schoolName = schoolNameView.text.toString()
        schoolCity = schoolCityView.text.toString()
        schoolCountry = schoolCountryView.text.toString()
    }
    alert.show()
}




