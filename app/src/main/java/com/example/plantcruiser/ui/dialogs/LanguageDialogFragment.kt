package com.example.plantcruiser.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.LayoutInflaterCompat
import androidx.fragment.app.DialogFragment
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.LanguageChangeDialogBinding
import com.example.plantcruiser.utils.HelperFunctions

class LanguageDialogFragment : DialogFragment() {

    var _binding : LanguageChangeDialogBinding? = null
    val binding get() = _binding!!
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val inflater = requireActivity().layoutInflater

            val builder = AlertDialog.Builder(it)

            val view : View = inflater.inflate(R.layout.language_change_dialog, null)

            // set the title of the dialog according to the current language
            builder.setTitle(it.getString(R.string.dialog_language_select))

            // get radio buttons' references
            val radioGroup = binding.radioGroupLanguages
            val radioButtonEnglish = binding.radioButtonEnglish
            val radioButtonHebrew = binding.radioButtonHebrew

            // set buttons state checked according to the currentLocale
            radioButtonEnglish.isChecked = it.currentLocale.equals("en", ignoreCase = true)
            radioButtonHebrew.isChecked = !radioButtonEnglish.isChecked

            // define a temporary variable to hold the new selected language
            var selectedLanguage = context.currentLocale

            // use RadioGroup setOnCheckedChangeListener to handle language changes dynamically
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                selectedLanguage = when (checkedId) {
                    R.id.radioButtonEnglish -> "en"
                    R.id.radioButtonHebrew -> "iw"
                    else -> it.currentLocale
                }
            }

            // if accepted and language was changed, call on changeLocale method to change language
            builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
                if (activity.currentLocale != selectedLanguage) {
                    HelperFunctions.setLocale(selectedLanguage)
                }
                dialog.dismiss()
            }

            // if cancelled, do nothing
            builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }

            // present dialog with animation
            val dialog = builder.create()
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            dialog.show()






            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            val inflater = LayoutInflaterCompat(it)
//            val dialogView : View = inflater.inflate(R.layout.language_change_dialog, null)
//            builder.setView(dialogView)
//
//            // set the title of the dialog according to the current language
//            builder.setTitle(context.getString(R.string.dialog_language_select))
//
//            // get radio buttons' references
//            val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroupLanguages)
//            val radioButtonEnglish = dialogView.findViewById<RadioButton>(R.id.radioButtonEnglish)
//            val radioButtonHebrew = dialogView.findViewById<RadioButton>(R.id.radioButtonHebrew)
//
//            // set buttons state checked according to the currentLocale
//            radioButtonEnglish.isChecked = context.currentLocale.equals("en", ignoreCase = true)
//            radioButtonHebrew.isChecked = !radioButtonEnglish.isChecked
//
//            // define a temporary variable to hold the new selected language
//            var selectedLanguage = context.currentLocale
//
//            // use RadioGroup setOnCheckedChangeListener to handle language changes dynamically
//            radioGroup.setOnCheckedChangeListener { _, checkedId ->
//                selectedLanguage = when (checkedId) {
//                    R.id.radioButtonEnglish -> "en"
//                    R.id.radioButtonHebrew -> "iw"
//                    else -> activity.currentLocale
//                }
//            }
//
//            // if accepted and language was changed, call on changeLocale method to change language
//            builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
//                if (activity.currentLocale != selectedLanguage) {
//                    activity.uiManager.setLocale(selectedLanguage)
//                }
//                dialog.dismiss()
//            }
//            // if cancelled, do nothing
//            builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }
//
//            // present dialog with animation
//            val dialog = builder.create()
//            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
//            dialog.show()
//        } ?: throw IllegalStateException("Null activity")
//
//    }
