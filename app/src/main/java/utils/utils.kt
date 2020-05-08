package utils

import android.content.Context
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner

fun createSpinnerView(context: Context, id: Int): Spinner{
    val spinnerView = Spinner(context)
    spinnerView.id = id
    return spinnerView
}

fun createEditTextView(context: Context, id: Int, hint: String): EditText{
    val editTextView = EditText(context)
    editTextView.hint = hint
    editTextView.id = id
    return editTextView
}

fun createHorizontalLayout(context: Context): LinearLayout{
    val horizontalLayout = LinearLayout(context)
    horizontalLayout.orientation = LinearLayout.HORIZONTAL
    horizontalLayout.gravity = Gravity.CENTER
    return horizontalLayout;
}