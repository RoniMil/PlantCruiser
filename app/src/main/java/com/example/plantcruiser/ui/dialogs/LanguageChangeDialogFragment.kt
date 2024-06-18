package com.example.plantcruiser.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.plantcruiser.R
import com.example.plantcruiser.databinding.LanguageChangeDialogBinding
import java.util.Locale

// dialog for showing the option to language change
class LanguageChangeDialogFragment : DialogFragment() {

    private var _binding: LanguageChangeDialogBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            _binding = LanguageChangeDialogBinding.inflate(LayoutInflater.from(context))

            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)

            // set the title of the dialog according to the current language
            builder.setTitle(it.getString(R.string.dialog_language_select))

            // get radio buttons' references
            val radioGroup = binding.radioGroupLanguages
            val radioButtonEnglish = binding.radioButtonEnglish
            val radioButtonHebrew = binding.radioButtonHebrew

            val currentLocale = resources.configuration.locales.get(0).toString()

            // set buttons state checked according to the currentLocale
            radioButtonEnglish.isChecked = currentLocale.contains("en", ignoreCase = true)
            radioButtonHebrew.isChecked = !radioButtonEnglish.isChecked

            // define a temporary variable to hold the new selected language
            var selectedLanguage = currentLocale

            // use RadioGroup setOnCheckedChangeListener to handle language changes dynamically
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                selectedLanguage = when (checkedId) {
                    R.id.radioButtonEnglish -> "en"
                    R.id.radioButtonHebrew -> "iw"
                    else -> currentLocale
                }
            }

            // if accepted and language was changed, call on changeLocale method to change language
            builder.setPositiveButton(R.string.dialog_accept) { dialog, _ ->
                if (currentLocale != selectedLanguage) {
                    setLocale(selectedLanguage)
                }
                dialog.dismiss()
            }

            // if cancelled, do nothing
            builder.setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.dismiss() }

            // present dialog with animation
            val dialog = builder.create()
            dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
            return dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    // This method changes the language in which the app is displayed
    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        // Refresh the activity to apply the new locale settings
        activity?.recreate()

    }
}


