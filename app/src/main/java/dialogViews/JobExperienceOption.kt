package dialogViews

import android.content.Context
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.example.androidportfolio.R
import java.util.*


fun jobExperienceView(context : Context){
    val days = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
    val months = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12);

    val verticalParentView = LinearLayout(context);
    verticalParentView.orientation = LinearLayout.VERTICAL;
    verticalParentView.gravity = Gravity.CENTER;

    val horizontalParentView = LinearLayout(context);
    horizontalParentView.orientation = LinearLayout.HORIZONTAL;
    horizontalParentView.gravity = Gravity.CENTER;

    val alert = AlertDialog.Builder(context)
    alert.setTitle("Job experience");

    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    val startDayView = Spinner(context);
    startDayView.id = R.id.startDay;
    val startMonthView = Spinner(context);
    startMonthView.id = R.id.startMonth;
    val startYearView = Spinner(context);
    startYearView.id = R.id.startYear;

    val dayAdapter = ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, days);
    val monthAdapter = ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, months);
    val yearAdapter =  ArrayAdapter<Int>(context, android.R.layout.simple_spinner_dropdown_item, createYearList());
    startDayView.adapter = dayAdapter;
    startMonthView.adapter = monthAdapter;
    startYearView.adapter = yearAdapter;



    horizontalParentView.addView(startDayView,layoutParams);
    horizontalParentView.addView(startMonthView,layoutParams);
    horizontalParentView.addView(startYearView,layoutParams);
    verticalParentView.addView(horizontalParentView);

    alert.setView(verticalParentView);
    alert.setPositiveButton("Done"){_, _ ->


    }
    alert.show()
}

fun createYearList(): MutableList<Int>{
    val years: MutableList<Int> = arrayListOf();
    var i = 1900;
    val currentYear = Calendar.YEAR;
    while (i <= currentYear){
        years.add(i);
        i++
    }
    return years;
}