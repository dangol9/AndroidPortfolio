package dialogViews

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.androidportfolio.R
import utils.createEditTextView
import utils.createHorizontalLayout
import utils.createSpinnerView
import utils.createYearList

var occupation : MutableList<EditText> = arrayListOf()
var employerName: MutableList<EditText> = arrayListOf()
var employerCity: MutableList<EditText> = arrayListOf()
var employerCountry : MutableList<EditText> = arrayListOf()
var duties : MutableList<EditText> = arrayListOf()
var jobStartDay : MutableList<Spinner> = arrayListOf()
var jobStartMonth : MutableList<Spinner> = arrayListOf()
var jobStartYear : MutableList<Spinner> = arrayListOf()
var jobEndDay : MutableList<Spinner> = arrayListOf()
var jobEndMonth : MutableList<Spinner> = arrayListOf()
var jobEndYear : MutableList<Spinner> = arrayListOf()

private val days = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31)
private val months = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)

fun jobExperienceView(context : Context){
    val alert = AlertDialog.Builder(context)
    alert.setTitle("Job experience")

    val verticalParentView = LinearLayout(context)
    verticalParentView.orientation = LinearLayout.VERTICAL
    verticalParentView.gravity = Gravity.CENTER

    val scrollView = ScrollView(context)

    addJobView(context, verticalParentView)

    scrollView.addView(verticalParentView)
    alert.setView(scrollView)

    alert.setPositiveButton("Done"){_, _ ->
    }
    alert.setNegativeButton("Add new", null)

    val alertDialog = alert.create()
    alertDialog.show()
    //so that it does not dismiss
    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
        addJobView(context, verticalParentView)
    }

}



private fun addJobView(context: Context, parent: LinearLayout){

    val startDatesHorizontalParentView = createHorizontalLayout(context)
    val endDatesHorizontalParentView = createHorizontalLayout(context)
    val childHorizontalView = createHorizontalLayout(context)

    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

    val fromTextView = TextView(context)
    fromTextView.text = "From"

    val startDayView = createSpinnerView(context, R.id.jobStartDay)
    val startMonthView = createSpinnerView(context, R.id.jobStartMonth)
    val startYearView = createSpinnerView(context, R.id.jobStartYear)

    val upToTextView = TextView(context)
    upToTextView.text = "Up to"

    val endDayView= createSpinnerView(context, R.id.jobEndDay)
    val endMonthView= createSpinnerView(context, R.id.jobEndMonth)
    val endYearView =createSpinnerView(context, R.id.jobEndYear)

    val dayAdapter = ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, days)
    val monthAdapter = ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, months)
    val yearAdapter =  ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, createYearList())

    startDayView.adapter = dayAdapter
    startMonthView.adapter = monthAdapter
    startYearView.adapter = yearAdapter

    endDayView.adapter = dayAdapter
    endMonthView.adapter = monthAdapter
    endYearView.adapter = yearAdapter

    val occupationView = createEditTextView(context,  R.id.occupation, "Occupation")
    val employerNameView = createEditTextView(context,  R.id.employerName, "Employer name")
    val employerCityView = createEditTextView(context,  R.id.employerCity, "City")
    val employerCountryView = createEditTextView(context,  R.id.employerCountry, "Country")

    val listOfDutiesView = createEditTextView(context,  R.id.duties, "Duties")
    listOfDutiesView.setLines(4)

    occupation.add(occupationView)
    employerName.add(employerNameView)
    employerCity.add(employerCityView)
    employerCountry.add(employerCountryView)
    duties.add(listOfDutiesView)
    jobStartDay.add(startDayView)
    jobStartMonth.add(startMonthView)
    jobStartYear.add(startYearView)
    jobEndDay.add(endDayView)
    jobEndMonth.add(endMonthView)
    jobEndYear.add(endYearView)


    startDatesHorizontalParentView.addView(fromTextView,layoutParams)
    startDatesHorizontalParentView.addView(startDayView,layoutParams)
    startDatesHorizontalParentView.addView(startMonthView,layoutParams)
    startDatesHorizontalParentView.addView(startYearView,layoutParams)
    parent.addView(startDatesHorizontalParentView)

    endDatesHorizontalParentView.addView(upToTextView,layoutParams)
    endDatesHorizontalParentView.addView(endDayView, layoutParams)
    endDatesHorizontalParentView.addView(endMonthView, layoutParams)
    endDatesHorizontalParentView.addView(endYearView, layoutParams)
    parent.addView(endDatesHorizontalParentView)

    parent.addView(occupationView)

    childHorizontalView.addView(employerNameView)
    childHorizontalView.addView(employerCityView)
    childHorizontalView.addView(employerCountryView)
    parent.addView(childHorizontalView)
    parent.addView(listOfDutiesView)

    val lineParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 5)
    val line = View(context)

    line.setBackgroundResource(R.color.colorPrimary)
    parent.addView(line, lineParams)

}
