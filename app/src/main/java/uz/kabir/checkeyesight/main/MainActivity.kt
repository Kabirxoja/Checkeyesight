package uz.kabir.checkeyesight.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import java.util.*
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.language.Constants


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var drawerLayout: DrawerLayout? = null
    private lateinit var listener: NavController.OnDestinationChangedListener

    private var language = ""
    private var lanCountry = ""

    companion object {
        // Using a constant to make the code cleaner.
        private const val MAX_BRIGHTNESS = 1F
    }

    // Our stored previous brightness.
    private var previousBrightness = MAX_BRIGHTNESS


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    private var navOptions: NavOptions? = null


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE)
        editor = sharedPreferences.edit()


        // Get the saved theme preference
        val isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false)


        // Set the new theme based on the current theme
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            //change status bar

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            //change status bar

        }

        supportActionBar?.setHomeAsUpIndicator(R.drawable.burger)


        saveChooseLanguage()

        navController = findNavController(R.id.fragment)
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        val navigateView = findViewById<NavigationView>(R.id.navigationView)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigateView.setupWithNavController(navController)

        //Drawer Header
        val headerLayout: View = navigateView.inflateHeaderView(R.layout.nav_header)
        val switchHeader: SwitchCompat =
            headerLayout.findViewById<View>(R.id.switch_id) as SwitchCompat
        switchHeader.isChecked = isDarkModeOn
        val headerView = navigateView.getHeaderView(0)


        switchHeader.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("isDarkModeOn", true)
                editor.apply()
                restartApp()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("isDarkModeOn", false)
                editor.apply()
                restartApp()
            }
        }

        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.my_navgraph)
        graph.setStartDestination(R.id.splashFragment)
        navHostFragment.navController.graph = graph

        supportActionBar?.setHomeAsUpIndicator(R.drawable.burger)



        listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.homeFragment -> {
                        window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                        graph.setStartDestination(R.id.homeFragment)
                        navHostFragment.navController.graph = graph

                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white_to_night)))
                        supportActionBar?.show()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        supportActionBar?.elevation = 0F
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.burger)


                        //change language drawer items
                        navigateView.menu.clear()
                        navigateView.inflateMenu(R.menu.drawer_menu)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                        val drawerLayout2: DrawerLayout = findViewById(R.id.drawer_layout)

                        navigateView.setNavigationItemSelectedListener { menuItem ->

                            // Initialize NavOptions with animations
                            navOptions = NavOptions.Builder()
                                .setEnterAnim(R.anim.nav_slide_in_right)
                                .setExitAnim(R.anim.nav_slide_out_left)
                                .setPopEnterAnim(R.anim.nav_slide_in_left)
                                .setPopExitAnim(R.anim.nav_slide_out_right)
                                .build()


                            // Handle menu item clicks here
                            when (menuItem.itemId) {

                                R.id.nav_choose_lan -> {
                                    Navigation.findNavController(this, R.id.fragment)
                                        .navigate(R.id.chooseLanguageFragment, null, navOptions)
                                }

                                R.id.nav_history -> {
                                    Navigation.findNavController(this, R.id.fragment)
                                        .navigate(R.id.viewResult, null, navOptions)
                                }

                                R.id.nav_alarm -> {
                                    Navigation.findNavController(this, R.id.fragment)
                                        .navigate(R.id.alarmMainScreen, null, navOptions)
                                }

                                R.id.nav_calculate -> {
                                    Navigation.findNavController(this, R.id.fragment)
                                        .navigate(R.id.calculate, null, navOptions)
                                }

                                R.id.nav_exit -> {
                                    finish()
                                }


                                // Add more cases for additional menu items
                            }

                            // Close the drawer
                            drawerLayout2.closeDrawer(GravityCompat.START)

                            true
                        }


                        previousBrightness()

                        changeStatusBarColorLight(isDarkModeOn)

                    }

                    R.id.splashFragment -> {
                        graph.setStartDestination(R.id.splashFragment)
                        navHostFragment.navController.graph = graph
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                    }


                    R.id.chooseLanguageFragment -> {
                        previousBrightness()

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                    }

                    R.id.closingLeftEye -> {
                        previousBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        changeStatusBarColorDark(isDarkModeOn)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.closingRightEye -> {
                        previousBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        changeStatusBarColorDark(isDarkModeOn)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.leftVisionEye -> {
                        fullBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        onlyWhiteColor()
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.rightVisionTest -> {
                        fullBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        onlyWhiteColor()
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.VPNewFragment -> {
                        previousBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        changeStatusBarColorDark(isDarkModeOn)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.chooseDistance -> {
                        supportActionBar?.show()
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                    }

                    R.id.swipeTestBySymbols -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        fullBrightness()
                        onlyWhiteColor()
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.swipeTestBySymbolsRight -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        fullBrightness()
                        onlyWhiteColor()
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.viewResult -> {
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.show()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        previousBrightness()
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.noteScreen -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.lineChartFragment -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        supportActionBar?.show()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                    }

                    R.id.colorBlindnessTest -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.choosingConnection -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.duochromeTest -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.resultColorBlindness -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        changeStatusBarColorDark(isDarkModeOn)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.resultScreen -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        changeStatusBarColorDark(isDarkModeOn)
                        previousBrightness()
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.readFragment -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.writeFragment -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.leftEyeTest -> {
                        previousBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        changeStatusBarColorDark(isDarkModeOn)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.rightEyeTest -> {
                        previousBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        changeStatusBarColorDark(isDarkModeOn)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.astigmatismTest -> {
                        fullBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        onlyWhiteColor()
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.resultAstigmatism -> {
                        previousBrightness()
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        onlyWhiteColor()
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.amslerGrid -> {
                        supportActionBar?.show()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.nearVisionTest -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                    }

                    R.id.mainFarsightedness -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.mainNearsightedness -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.mainRecovery -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.mainRelaxation -> {
                        supportActionBar?.hide()
                        supportActionBar?.setShowHideAnimationEnabled(false)
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.contrastVisionTest -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.calculate -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.showResultGlass2 -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }

                    R.id.alarmMainScreen -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            supportActionBar?.title =
                                Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
                        }
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
                        changeStatusBarColorDark(isDarkModeOn)
                        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }


                }
            }
    }


    override fun onStart() {
        navController.addOnDestinationChangedListener(listener)
        super.onStart()
    }


    override fun onPause() {
        navController.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        menu?.findItem(R.id.info_uz)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }


    private fun saveChooseLanguage() {
        val shp: SharedPreferences = applicationContext.getSharedPreferences(
            Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE
        )
        language = shp.getString(Constants.LANGUAGE, "")!!
        lanCountry = shp.getString(Constants.LANGUAGE_COUNTRY, "")!!
        val locale = Locale(language, lanCountry)

        println("lan  $language")

        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        applicationContext.resources?.updateConfiguration(
            config,
            applicationContext.resources?.displayMetrics
        )
    }


    private fun restartApp() {
        startActivity(Intent(applicationContext, this@MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        val attributes = window.attributes
        // Set the brightness to previousBrightness.
        attributes.screenBrightness = previousBrightness
        window.attributes = attributes
        // Don't forget to called super.onDestroy()
        super.onDestroy()
    }

    private fun fullBrightness() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val params = window.attributes
        params.screenBrightness = 1.0f
        window.attributes = params
    }

    private fun previousBrightness() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val params = window.attributes
        params.screenBrightness =
            WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
        window.attributes = params
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            saveChooseLanguage()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            saveChooseLanguage()
        }
    }

    private fun changeStatusBarColorLight(isDarkModeOn: Boolean) {
        if (isDarkModeOn) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.only_night)
        } else {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.white)

        }
    }

    private fun changeStatusBarColorDark(isDarkModeOn: Boolean) {
        if (isDarkModeOn) {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.only_night)
        } else {
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.main_color)

        }
    }

    private fun onlyWhiteColor() {
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.white)
    }


}



