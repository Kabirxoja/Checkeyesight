package uz.kabir.checkeyesight.language

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentChooseLanguageBinding
import uz.kabir.checkeyesight.duochrometest.Constant
import uz.kabir.checkeyesight.language.Constants.KEY_PENDING_NAV
import java.util.*
import androidx.core.content.edit

class ChooseLanguageFragment : Fragment() {

    private var viewBinding: FragmentChooseLanguageBinding? = null
    private val binding get() = viewBinding!!

    private var selectedLanguageCountry = ""
    private var selectedLanguageCode = ""

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        view?.findNavController()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentChooseLanguageBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref =
            context?.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
        val getCountry = sharedPref!!.getString(Constants.LANGUAGE, "")

        val languages = listOf(
            Language("uz", "UZ", binding.btnUzbekSelected, binding.txtUzbek),
            Language("en", "US", binding.btnEnglishSelected, binding.txtUk),
            Language("ru", "RU", binding.btnRussianSelected, binding.txtRussia),
            Language("kaa", "", binding.btnKarakalpakSelected, binding.txtKarakalpak),
            Language("fr", "Re", binding.btnFrenchSelected, binding.txtFrance),
            Language("es", "ES", binding.btnSpanishSelected, binding.txtSpain),
            Language("pt", "PT", binding.btnPortugueseSelected, binding.txtPortugal),
        )


        languages.forEach { language ->
            language.button.setOnClickListener {
                selectedLanguageCode = language.code
                selectedLanguageCountry = language.country
                binding.complete.visibility = View.VISIBLE
                updateUI(language, languages)
                saveSelectedLanguage(selectedLanguageCode, selectedLanguageCountry)
            }
        }


        languages.find {
            it.code == getCountry
        }?.let {
            updateUI(it, languages)
        }


        binding.complete.setOnClickListener {

            saveSelectedLanguage(selectedLanguageCode, selectedLanguageCountry)

            val pref = requireContext().getSharedPreferences(
                Constants.SHARED_PREFERENCE_NAME,
                MODE_PRIVATE
            )

            pref.edit()
                .putBoolean(KEY_PENDING_NAV, true)
                .apply()

            LanguageHelper.applyAppLocale(
                requireContext(),
                selectedLanguageCode,
                selectedLanguageCountry
            )
        }


    }

    fun updateUI(selectedLanguage: Language, allLanguage: List<Language>) {
        allLanguage.forEach { language ->
            if (language.code == selectedLanguage.code) {
                language.button.setBackgroundResource(R.drawable.bg_language_select)
                language.textView.setTextColor(resources.getColor(R.color.dark_and_light))
            } else {
                language.button.setBackgroundResource(R.drawable.bg_language_unselect)
                language.textView.setTextColor(resources.getColor(R.color.white))
            }
        }
    }


    private fun saveSelectedLanguage(language: String, country: String) {

        if (language.isBlank() || country.isBlank()) return

        // get shared preference
        val pref = requireContext().getSharedPreferences(
            Constants.SHARED_PREFERENCE_NAME,
            MODE_PRIVATE
        )
        pref.edit()
            .putString(Constants.LANGUAGE, language)
            .putString(Constants.LANGUAGE_COUNTRY, country)
            .apply()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.removeItem(R.id.info_uz)
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

}
