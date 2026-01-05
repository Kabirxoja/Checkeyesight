package uz.kabir.checkeyesight

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import uz.kabir.checkeyesight.language.Constants
import uz.kabir.checkeyesight.language.LanguageHelper

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener
    private lateinit var navController: NavController
    private var navOptions: NavOptions? = null
    private var drawerLayout: DrawerLayout? = null
    private var previousBrightness = MAX_BRIGHTNESS
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object {
        private const val MAX_BRIGHTNESS = 1F
        private const val APP_PACKAGE_NAME = "uz.kabir.checkeyesight"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handlePendingNotification(intent)
        initializePreferences()
        setupNavigation()
        setupNavigationDrawer()
        setupNavigationGraph()
        setupDestinationListener()

    }
    private fun initializePreferences() {
        sharedPreferences = getSharedPreferences("theme", MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeOn) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
    private fun setupNavigation() {
        navController = findNavController(R.id.fragment)
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_burger)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupNavigationDrawer() {
        val navigateView = findViewById<NavigationView>(R.id.navigationView)
        navigateView.setupWithNavController(navController)
        val headerLayout: View = navigateView.inflateHeaderView(R.layout.nav_header)
        val switchHeader: SwitchCompat = headerLayout.findViewById(R.id.switch_id)
        val isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false)
        switchHeader.isChecked = isDarkModeOn
        switchHeader.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
            editor.putBoolean("isDarkModeOn", isChecked).apply()
            restartApp()
        }
    }

    private fun restartApp() {
        startActivity(Intent(applicationContext, this@MainActivity::class.java))
        finish()
    }

    private fun setupNavigationGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val graph = navHostFragment.navController.navInflater.inflate(R.navigation.my_navgraph)
        graph.setStartDestination(R.id.splashFragment)
        navHostFragment.navController.graph = graph
    }
    private fun setupDestinationListener() {
        val isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false)
        listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> setupHomeFragment(isDarkModeOn)
                R.id.splashFragment -> setupSplashFragment()
                R.id.chooseLanguageFragment, R.id.viewResult,
                R.id.lineChartFragment, R.id.colorBlindnessTest, R.id.duochromeTest,
                R.id.amslerGrid, R.id.contrastVisionTest, R.id.calculate,
                R.id.showResultGlass2, R.id.alarmMainScreen, R.id.chooseDistance -> setupActionBarFragment(isDarkModeOn)
                R.id.closingLeftEye, R.id.closingRightEye, R.id.VPNewFragment,
                R.id.leftEyeTest, R.id.rightEyeTest, R.id.resultColorBlindness,
                R.id.resultScreen, R.id.readFragment, R.id.writeFragment -> setupHiddenActionBarFragment(isDarkModeOn)
                R.id.leftVisionEye, R.id.rightVisionTest, R.id.astigmatismTest -> setupFullBrightnessFragment()
                R.id.swipeTestBySymbols, R.id.swipeTestBySymbolsRight -> setupSwipeTestFragment()
                R.id.resultAstigmatism -> setupResultAstigmatismFragment()
                R.id.choosingConnection, R.id.nearVisionTest, R.id.mainFarsightedness,
                R.id.mainNearsightedness, R.id.mainRecovery, R.id.mainRelaxation -> setupSimpleHiddenFragment()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupHomeFragment(isDarkModeOn: Boolean) {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.white_to_night)))
            show()
            setShowHideAnimationEnabled(false)
            elevation = 0F
            setHomeAsUpIndicator(R.drawable.icon_burger)
        }

        setupDrawerMenu()
        previousBrightness()
        changeStatusBarColorLight(isDarkModeOn)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
    @SuppressLint("RestrictedApi")
    private fun setupSplashFragment() {
        supportActionBar?.apply {
            hide()
            setShowHideAnimationEnabled(false)
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    @SuppressLint("RestrictedApi")
    private fun setupActionBarFragment(isDarkModeOn: Boolean) {
        supportActionBar?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                title = Html.fromHtml("<font color='${getColor(R.color.dark_and_light)}'></font>")
            }
            setHomeAsUpIndicator(R.drawable.back)
            setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.dark_night)))
            show()
            setShowHideAnimationEnabled(false)
        }
        previousBrightness()
        changeStatusBarColorDark(isDarkModeOn)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    @SuppressLint("RestrictedApi")
    private fun setupHiddenActionBarFragment(isDarkModeOn: Boolean) {
        supportActionBar?.apply {
            hide()
            setShowHideAnimationEnabled(false)
        }
        previousBrightness()
        changeStatusBarColorDark(isDarkModeOn)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    @SuppressLint("RestrictedApi")
    private fun setupFullBrightnessFragment() {
        fullBrightness()
        supportActionBar?.apply {
            hide()
            setShowHideAnimationEnabled(false)
        }
        onlyWhiteColor()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    private fun setupSwipeTestFragment() {
        fullBrightness()
        onlyWhiteColor()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    @SuppressLint("RestrictedApi")
    private fun setupResultAstigmatismFragment() {
        previousBrightness()
        supportActionBar?.apply {
            hide()
            setShowHideAnimationEnabled(false)
        }
        onlyWhiteColor()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    @SuppressLint("RestrictedApi")
    private fun setupSimpleHiddenFragment() {
        supportActionBar?.apply {
            hide()
            setShowHideAnimationEnabled(false)
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun setupDrawerMenu() {
        val navigateView = findViewById<NavigationView>(R.id.navigationView)
        val drawerLayout2: DrawerLayout = findViewById(R.id.drawer_layout)

        navigateView.menu.clear()
        navigateView.inflateMenu(R.menu.drawer_menu)

        navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.nav_slide_in_right)
            .setExitAnim(R.anim.nav_slide_out_left)
            .setPopEnterAnim(R.anim.nav_slide_in_left)
            .setPopExitAnim(R.anim.nav_slide_out_right)
            .build()

        navigateView.setNavigationItemSelectedListener { menuItem ->
            handleDrawerMenuClick(menuItem.itemId)
            drawerLayout2.closeDrawer(GravityCompat.START)
            true
        }
    }
    private fun onlyWhiteColor() {
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.white)
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



    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        handlePendingNavigation()
    }
    private fun handlePendingNavigation() {
        val pref = getSharedPreferences(
            Constants.SHARED_PREFERENCE_NAME,
            MODE_PRIVATE
        )

        val shouldNavigate = pref.getBoolean(Constants.KEY_PENDING_NAV, false)
        if (!shouldNavigate) return

        pref.edit().putBoolean(Constants.KEY_PENDING_NAV, false).apply()
        val onBoardingFinished = getSharedPreferences("onBoarding", MODE_PRIVATE).getBoolean("Finished", false)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        if (onBoardingFinished) {
            navController.navigate(R.id.homeFragment)
        } else {
            navController.navigate(R.id.viewPagerFragment)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handlePendingNotification(intent)
    }
    private fun handlePendingNotification(intent: Intent?) {
        /** !! it didn't work, you should try again (i want to get and set tab index to homeFragment) ***
         val tabIndex = intent?.getIntExtra("OPEN_TAB_INDEX", -1) ?: return
         if (tabIndex == -1) return
        val entry = navController.getBackStackEntry(R.id.homeFragment)
        entry.savedStateHandle.set("OPEN_TAB_INDEX", tabIndex)
        */
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        if (navController.currentDestination?.id != R.id.homeFragment) {
            navController.navigate(R.id.homeFragment)
        }
    }


    override fun attachBaseContext(newBase: Context) {
        val wrapped = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            newBase
        } else {
            LanguageHelper.wrapContext(newBase)
        }
        super.attachBaseContext(wrapped)
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




    private fun handleDrawerMenuClick(itemId: Int) {
        val navController = Navigation.findNavController(this, R.id.fragment)

        when (itemId) {
            R.id.nav_choose_lan -> navController.navigate(R.id.chooseLanguageFragment, null, navOptions)
            R.id.nav_history -> navController.navigate(R.id.viewResult, null, navOptions)
            R.id.nav_alarm -> navController.navigate(R.id.alarmMainScreen, null, navOptions)
            R.id.nav_calculate -> navController.navigate(R.id.calculate, null, navOptions)
            R.id.nav_share -> shareApp()
            R.id.nav_rate -> rateApp()
            R.id.nav_exit -> finish()
        }
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "https://play.google.com/store/apps/details?id=$APP_PACKAGE_NAME"
            )
        }
        val chooser = Intent.createChooser(shareIntent, "Share via")
        startActivity(chooser)
    }
    private fun rateApp() {
        try{
            val marketIntent = Intent(
                Intent.ACTION_VIEW,
                "market://details?id=$APP_PACKAGE_NAME".toUri()
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(marketIntent)
        }catch(e:Exception){
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=$APP_PACKAGE_NAME".toUri()
            ).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(webIntent)
        }
    }


    override fun onDestroy() {
        val attributes = window.attributes
        // Set the brightness to previousBrightness.
        attributes.screenBrightness = previousBrightness
        window.attributes = attributes
        // Don't forget to called super.onDestroy()
        super.onDestroy()
    }


}