package dialogViews

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.androidportfolio.R
import java.util.*
import utils.createEditTextView
import utils.createHorizontalLayout
import utils.createSpinnerView
import utils.createYearList

var eduStartDay : MutableList<Spinner> = arrayListOf()
var eduStartMonth : MutableList<Spinner> = arrayListOf()
var eduStartYear : MutableList<Spinner> = arrayListOf()
var eduEndDay : MutableList<Spinner> = arrayListOf()
var eduEndMonth : MutableList<Spinner> = arrayListOf()
var eduEndYear : MutableList<Spinner> = arrayListOf()
var schoolName : MutableList<EditText> = arrayListOf()
var schoolCity : MutableList<EditText> = arrayListOf()
var schoolCountry : MutableList<EditText> = arrayListOf()

private val days = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31)
private val months = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)

fun educationView(context : Context){
    val alert = AlertDialog.Builder(context)
    alert.setTitle("Education")

    val verticalParentView = LinearLayout(context)
    verticalParentView.orientation = LinearLayout.VERTICAL
    verticalParentView.gravity = Gravity.CENTER

    val scrollView = ScrollView(context)

    addEducationView(context, verticalParentView)

    scrollView.addView(verticalParentView)
    alert.setView(scrollView)

    alert.setPositiveButton("Done"){_, _ ->
    }
    alert.setNegativeButton("Add new", null)
    val alertDialog = alert.create()
    alertDialog.show()

    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
        addEducationView(context, verticalParentView)
    }
}

private fun addEducationView(context: Context, parent: LinearLayout){
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

    eduStartDay.add(startDayView)
    eduStartMonth.add(startMonthView)
    eduStartYear.add(startYearView)
    eduEndDay.add(endDayView)
    eduEndMonth.add(endMonthView)
    eduEndYear.add(endYearView)
    schoolName.add(schoolNameView)
    schoolCity.add(schoolCityView)
    schoolCountry.add(schoolCountryView)




    fromHorizontalParentView.addView(fromTextView,layoutParams)
    fromHorizontalParentView.addView(startDayView,layoutParams)
    fromHorizontalParentView.addView(startMonthView,layoutParams)
    fromHorizontalParentView.addView(startYearView,layoutParams)
    parent.addView(fromHorizontalParentView)

    upToHorizontalParentView.addView(upToTextView,layoutParams)
    upToHorizontalParentView.addView(endDayView, layoutParams)
    upToHorizontalParentView.addView(endMonthView, layoutParams)
    upToHorizontalParentView.addView(endYearView, layoutParams)
    parent.addView(upToHorizontalParentView)
    parent.addView(schoolNameView);
    detailsHorizontalParentView.addView(schoolCityView, layoutParams)
    detailsHorizontalParentView.addView(schoolCountryView, layoutParams)
    parent.addView(detailsHorizontalParentView)

    val lineParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5)
    val line = View(context)
    line.setBackgroundResource(R.color.colorPrimary)

    parent.addView(line, lineParams)

}




