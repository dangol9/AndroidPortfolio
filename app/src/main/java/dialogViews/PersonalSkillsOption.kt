package dialogViews

import android.content.Context
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.androidportfolio.R
import utils.createEditTextView
import utils.createHorizontalLayout
import utils.createSpinnerView

var nativeLanguage = ""
var listOfForeignLangauges = ""
var communicationSkill = ""
var leadingSkill = ""
var listOfJobSkills = ""
var listOfDigitalSkills = ""


fun personalSkillsView(context : Context){
    val alert = AlertDialog.Builder(context)
    alert.setTitle("Personal skills")

    val verticalParentView = LinearLayout(context)
    verticalParentView.orientation = LinearLayout.VERTICAL
    verticalParentView.gravity = Gravity.CENTER

    val horizontalParentView = createHorizontalLayout(context)

    val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)


    val nativeLanguageView = createEditTextView(context, R.id.nativeLanguage, "Native language")
    val communicationSkillView = createSpinnerView(context, R.id.communicationSkill)
    val leadingSkillView = createSpinnerView(context, R.id.leadingSkill)
    val foreignLanguages = createEditTextView(context, R.id.foreignLanguages, "Foreign langauges here")
    val jobSkillsView = createEditTextView(context, R.id.jobSkills, "Write your job skills here")
    jobSkillsView.setLines(3)
    val digitalSkillsView= createEditTextView(context, R.id.digitalSkills, "Write your digital skills here")
    digitalSkillsView.setLines(3)


    val choices = arrayOf("very good", "good", "decent", "bad", "very bad")
    val choiceAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, choices)
    communicationSkillView.adapter = choiceAdapter
    leadingSkillView.adapter = choiceAdapter



    verticalParentView.addView(nativeLanguageView)
    verticalParentView.addView(foreignLanguages)
    horizontalParentView.addView(communicationSkillView, layoutParams)
    horizontalParentView.addView(leadingSkillView, layoutParams)
    verticalParentView.addView(horizontalParentView)
    verticalParentView.addView(jobSkillsView)
    verticalParentView.addView(digitalSkillsView)

    alert.setView(verticalParentView)

    alert.setPositiveButton("Done"){_, _ ->
        nativeLanguage = nativeLanguageView.text.toString()
        listOfForeignLangauges = foreignLanguages.text.toString()
        communicationSkill = communicationSkillView.selectedItem.toString()
        leadingSkill = leadingSkillView.selectedItem.toString()
        listOfJobSkills = jobSkillsView.text.toString()
        listOfDigitalSkills = digitalSkillsView.text.toString()
    }
    alert.show()
}




