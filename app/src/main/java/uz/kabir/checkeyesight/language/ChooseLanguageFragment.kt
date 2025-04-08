package uz.kabir.checkeyesight.language

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentChooseLanguageBinding
import java.util.*

class ChooseLanguageFragment : Fragment() {

    private var viewBinding: FragmentChooseLanguageBinding? = null
    private val binding get() = viewBinding!!

    private var languageCountry = ""
    private var languageCode = ""

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

        changeTranslate(false,"","")
        
        val sharedPref = context?.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val getCountry = sharedPref!!.getString(Constants.LANGUAGE, "")

        when (getCountry) {
            "uz" -> {
                binding.txtUzbek.setTextColor(resources.getColor(R.color.dark_and_light))
                binding.btnUzbekSelected.setBackgroundResource(R.drawable.bg_language_select)
            }
            "en" -> {
                binding.txtUk.setTextColor(resources.getColor(R.color.dark_and_light))
                binding.btnEnglishSelected.setBackgroundResource(R.drawable.bg_language_select)
            }
            "ru" -> {
                binding.txtRussia.setTextColor(resources.getColor(R.color.dark_and_light))
                binding.btnRussianSelected.setBackgroundResource(R.drawable.bg_language_select)
            }
            "kaa" -> {
                binding.txtKarakalpak.setTextColor(resources.getColor(R.color.dark_and_light))
                binding.btnKarakalpakSelected.setBackgroundResource(R.drawable.bg_language_select)
            }
            "fr" -> {
                binding.txtFrance.setTextColor(resources.getColor(R.color.dark_and_light))
                binding.btnFrenchSelected.setBackgroundResource(R.drawable.bg_language_select)
            }
            "es" -> {
                binding.txtSpain.setTextColor(resources.getColor(R.color.dark_and_light))
                binding.btnSpanishSelected.setBackgroundResource(R.drawable.bg_language_select)
            }
            "pt" -> {
                binding.txtPortugal.setTextColor(resources.getColor(R.color.dark_and_light))
                binding.btnPortugueseSelected.setBackgroundResource(R.drawable.bg_language_select)
            }

        }


        binding.btnUzbekSelected.setOnClickListener {
            changeTranslate(true,"uz","UZ")

            binding.btnKarakalpakSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnUzbekSelected.setBackgroundResource(R.drawable.bg_language_select)
            binding.btnEnglishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnRussianSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.btnSpanishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnFrenchSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnPortugueseSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.txtUzbek.setTextColor(resources.getColor(R.color.dark_and_light))
            binding.txtRussia.setTextColor(resources.getColor(R.color.white))
            binding.txtUk.setTextColor(resources.getColor(R.color.white))
            binding.txtPortugal.setTextColor(resources.getColor(R.color.white))
            binding.txtSpain.setTextColor(resources.getColor(R.color.white))
            binding.txtFrance.setTextColor(resources.getColor(R.color.white))
            binding.txtKarakalpak.setTextColor(resources.getColor(R.color.white))

            languageCode = "uz"
            languageCountry = "UZ"

        }
        binding.btnEnglishSelected.setOnClickListener {
            changeTranslate(true,"en","US")

            binding.btnKarakalpakSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnUzbekSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnEnglishSelected.setBackgroundResource(R.drawable.bg_language_select)
            binding.btnRussianSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.btnSpanishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnFrenchSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnPortugueseSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.txtUk.setTextColor(resources.getColor(R.color.dark_and_light))
            binding.txtRussia.setTextColor(resources.getColor(R.color.white))
            binding.txtKarakalpak.setTextColor(resources.getColor(R.color.white))
            binding.txtPortugal.setTextColor(resources.getColor(R.color.white))
            binding.txtSpain.setTextColor(resources.getColor(R.color.white))
            binding.txtFrance.setTextColor(resources.getColor(R.color.white))
            binding.txtUzbek.setTextColor(resources.getColor(R.color.white))

            languageCode = "en"
            languageCountry = "US"

        }
        binding.btnRussianSelected.setOnClickListener {
            changeTranslate(true,"ru","RU")
            binding.btnKarakalpakSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnUzbekSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnEnglishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnRussianSelected.setBackgroundResource(R.drawable.bg_language_select)

            binding.btnSpanishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnFrenchSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnPortugueseSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.txtRussia.setTextColor(resources.getColor(R.color.dark_and_light))
            binding.txtKarakalpak.setTextColor(resources.getColor(R.color.white))
            binding.txtUk.setTextColor(resources.getColor(R.color.white))
            binding.txtPortugal.setTextColor(resources.getColor(R.color.white))
            binding.txtSpain.setTextColor(resources.getColor(R.color.white))
            binding.txtFrance.setTextColor(resources.getColor(R.color.white))
            binding.txtUzbek.setTextColor(resources.getColor(R.color.white))

            languageCode = "ru"
            languageCountry = "RU"
        }

        binding.btnKarakalpakSelected.setOnClickListener {
            changeTranslate(true,"kaa","")
            binding.btnKarakalpakSelected.setBackgroundResource(R.drawable.bg_language_select)
            binding.btnUzbekSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnEnglishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnRussianSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.btnSpanishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnFrenchSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnPortugueseSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.txtKarakalpak.setTextColor(resources.getColor(R.color.dark_and_light))
            binding.txtRussia.setTextColor(resources.getColor(R.color.white))
            binding.txtUk.setTextColor(resources.getColor(R.color.white))
            binding.txtPortugal.setTextColor(resources.getColor(R.color.white))
            binding.txtSpain.setTextColor(resources.getColor(R.color.white))
            binding.txtFrance.setTextColor(resources.getColor(R.color.white))
            binding.txtUzbek.setTextColor(resources.getColor(R.color.white))

            languageCode = "kaa"
            languageCountry = ""

        }


        binding.btnFrenchSelected.setOnClickListener {
            changeTranslate(true,"fr","rRe")
            binding.btnFrenchSelected.setBackgroundResource(R.drawable.bg_language_select)
            binding.btnUzbekSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnEnglishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnRussianSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnSpanishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnPortugueseSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnKarakalpakSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.txtFrance.setTextColor(resources.getColor(R.color.dark_and_light))
            binding.txtRussia.setTextColor(resources.getColor(R.color.white))
            binding.txtUk.setTextColor(resources.getColor(R.color.white))
            binding.txtPortugal.setTextColor(resources.getColor(R.color.white))
            binding.txtSpain.setTextColor(resources.getColor(R.color.white))
            binding.txtKarakalpak.setTextColor(resources.getColor(R.color.white))
            binding.txtUzbek.setTextColor(resources.getColor(R.color.white))

            languageCode = "fr"
            languageCountry = "rRe"

        }



        binding.btnSpanishSelected.setOnClickListener {
            changeTranslate(true,"es","rES")
            binding.btnSpanishSelected.setBackgroundResource(R.drawable.bg_language_select)
            binding.btnUzbekSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnEnglishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnRussianSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnFrenchSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnPortugueseSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.txtSpain.setTextColor(resources.getColor(R.color.dark_and_light))
            binding.txtRussia.setTextColor(resources.getColor(R.color.white))
            binding.txtUk.setTextColor(resources.getColor(R.color.white))
            binding.txtPortugal.setTextColor(resources.getColor(R.color.white))
            binding.txtFrance.setTextColor(resources.getColor(R.color.white))
            binding.txtKarakalpak.setTextColor(resources.getColor(R.color.white))
            binding.txtUzbek.setTextColor(resources.getColor(R.color.white))

            languageCode = "es"
            languageCountry = "rES"

        }

        binding.btnPortugueseSelected.setOnClickListener {
            changeTranslate(true,"pt","rPT")
            binding.btnPortugueseSelected.setBackgroundResource(R.drawable.bg_language_select)
            binding.btnUzbekSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnEnglishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnRussianSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnSpanishSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnKarakalpakSelected.setBackgroundResource(R.drawable.bg_language_unselect)
            binding.btnFrenchSelected.setBackgroundResource(R.drawable.bg_language_unselect)

            binding.txtPortugal.setTextColor(resources.getColor(R.color.dark_and_light))
            binding.txtRussia.setTextColor(resources.getColor(R.color.white))
            binding.txtUk.setTextColor(resources.getColor(R.color.white))
            binding.txtFrance.setTextColor(resources.getColor(R.color.white))
            binding.txtSpain.setTextColor(resources.getColor(R.color.white))
            binding.txtKarakalpak.setTextColor(resources.getColor(R.color.white))
            binding.txtUzbek.setTextColor(resources.getColor(R.color.white))

            languageCode = "pt"
            languageCountry = "rPT"

        }




    }

    private fun changeTranslate(booleanEnabledButton:Boolean,languageCode: String,languageCountry: String)
    {
        if (booleanEnabledButton)
        {
            binding.complete.visibility = View.VISIBLE

            binding.complete.setOnClickListener {
                setLanguage(languageCode,languageCountry)
                if (onBoarding()) {
                    navController?.navigate(ChooseLanguageFragmentDirections.actionChooseLanguageFragmentToHomeFragment())
                } else {
                    navController?.navigate(ChooseLanguageFragmentDirections.actionChooseLanguageFragmentToViewPagerFragment())
                }
            }
        }
    }


    private fun setLanguage(languageCode: String, languageCountry: String) {



        Log.i("mylan",languageCode.toString())

        val locale = Locale(languageCode, languageCountry)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        activity?.baseContext?.resources?.updateConfiguration(
            config,
            activity?.baseContext?.resources?.displayMetrics
        )
        //saqlashga yuborish
        val editor = activity?.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)?.edit()
        editor?.putString(Constants.LANGUAGE, languageCode) //uz_UZ ?!
        editor?.putString(Constants.LANGUAGE_COUNTRY, languageCountry)
        editor?.putBoolean(Constants.IS_REGISTERED, true)
        editor?.apply()
    }

    private fun onBoarding(): Boolean {
        val sharedPref = context?.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref!!.getBoolean("Finished", false)
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
