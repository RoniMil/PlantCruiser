package com.example.plantcruiser.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.LayoutInflaterCompat
import com.example.plantcruiser.R

class HelperFunctions {
    // this method is responsible for displaying the dialog for changing languages
    fun showLanguageDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflaterCompat(context)
        val dialogView = inflater.inflate(R.layout.language_change_dialog, null)
        builder.setView(dialogView)

        // set the title of the dialog according to the current language
        builder.setTitle(context.getString(R.string.dialog_language_select))

        // get radio buttons' references
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroupLanguages)
        val radioButtonEnglish = dialogView.findViewById<RadioButton>(R.id.radioButtonEnglish)
        val radioButtonHebrew = dialogView.findViewById<RadioButton>(R.id.radioButtonHebrew)

        // set buttons state checked according to the currentLocale
        radioButtonEnglish.isChecked = context.currentLocale.equals("en", ignoreCase = true)
        radioButtonHebrew.isChecked = !radioButtonEnglish.isChecked

        // define a temporary variable to hold the new selected language
        var selectedLanguage = context.currentLocale

        // use RadioGroup setOnCheckedChangeListener to handle language changes dynamically
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedLanguage = when (checkedId) {
                R.id.radioButtonEnglish -> "en"
                R.id.radioButtonHebrew -> "iw"
                else -> activity.currentLocale
            }
        }

        // if accepted and language was changed, call on changeLocale method to change language
        builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
            if (activity.currentLocale != selectedLanguage) {
                activity.uiManager.setLocale(selectedLanguage)
            }
            dialog.dismiss()
        }
        // if cancelled, do nothing
        builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }

        // present dialog with animation
        val dialog = builder.create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.show()
    }
}