package uz.kabir.checkeyesight.language

import android.app.LocaleManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import uz.kabir.checkeyesight.main.MainActivity
import java.util.Locale

object LanguageHelper {

    fun applySavedLocale(context: Context) {

        val pref = context.getSharedPreferences(
            Constants.SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

        val lang = pref.getString(Constants.LANGUAGE, null)
        val country = pref.getString(Constants.LANGUAGE_COUNTRY, null)

        val safeLang = if (lang.isNullOrBlank()) "en" else lang
        val safeCountry = if (country.isNullOrBlank()) "US" else country


        Log.d("tilni", "applySavedLocale: $safeLang $safeLang")

        applyAppLocale(context, safeLang, safeCountry)
    }


    fun applyAppLocale(context: Context, language: String, country: String) {

        val localeTag = "$language-$country"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = context.getSystemService(LocaleManager::class.java)
            localeManager.applicationLocales =
                LocaleList.forLanguageTags(localeTag)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(localeTag)
            )
        }
    }


    fun wrapContext(context: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return context
        }
        val pref = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val language = pref.getString(Constants.LANGUAGE, "uz")
        val country = pref.getString(Constants.LANGUAGE_COUNTRY, "UZ")
        val locale = Locale(language, country)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}