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

var jobStartDate = ""
var jobEndDate = ""
var occupation = ""
var employerName = ""
var employerCity = ""
var employerCountry = ""
var duties = ""

fun jobExperienceView(context : Context){
    val alert = AlertDialog.Builder(context)
    alert.setTitle("Job experience")

    val days = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31)
    val months = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)

    val verticalParentView = LinearLayout(context)
    verticalParentView.orientation = LinearLayout.VERTICAL
    verticalParentView.gravity = Gravity.CENTER

    val whole = LinearLayout(context)
    whole.orientation = LinearLayout.VERTICAL
    whole.gravity = Gravity.CENTER


    val fromHorizontalParentView = createHorizontalLayout(context)
    val upToHorizontalParentView = createHorizontalLayout(context)
    val detailsHorizontalParentView = createHorizontalLayout(context)

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

    val button = Button(context)
    button.id = R.id.buttonId
    button.text = "Click here for more"

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
    verticalParentView.addView(occupationView)
    verticalParentView.addView(employerNameView, layoutParams)
    detailsHorizontalParentView.addView(employerCityView, layoutParams)
    detailsHorizontalParentView.addView(employerCountryView, layoutParams)
    verticalParentView.addView(detailsHorizontalParentView)
    verticalParentView.addView(listOfDutiesView)
    verticalParentView.addView(button)

    alert.setView(verticalParentView)

    alert.setPositiveButton("Done"){_, _ ->
        jobStartDate = startDayView.selectedItem.toString() + "/" + startMonthView.selectedItem.toString() + "/" + startYearView.selectedItem.toString()
        jobEndDate = endDayView.selectedItem.toString() + "/" + endMonthView.selectedItem.toString() + "/" + endYearView.selectedItem.toString()
        occupation = occupationView.text.toString()
        employerName = employerNameView.text.toString()
        employerCity = employerCityView.text.toString()
        employerCountry = employerCountryView.text.toString()
        duties = listOfDutiesView.text.toString()

    }
    alert.show()
}

fun createYearList(): MutableList<Int>{
    val years: MutableList<Int> = arrayListOf()
    var i = 1920
    var currentYear = Calendar.getInstance().get(Calendar.YEAR)
    while (i <= currentYear){
        years.add(currentYear--)
    }
    return years
}
