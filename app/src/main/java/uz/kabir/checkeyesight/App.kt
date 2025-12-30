package uz.kabir.checkeyesight

import android.app.Application
import uz.kabir.checkeyesight.language.LanguageHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LanguageHelper.applySavedLocale(this)
    }
}
