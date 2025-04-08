package uz.kabir.checkeyesight.splash


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            // Starting from Android 11, the status bar cannot be hidden completely
//            // Use the WindowInsetsController to adjust the behavior of the status bar
//            val controller = requireActivity().window.insetsController
//            if (controller != null) {
//                controller.hide(WindowInsets.Type.statusBars())
//                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        } else {
//            // For devices running on Android 10 or below
//            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Handler().postDelayed({
            lifecycleScope.launchWhenResumed {
                if (onBoarding()) {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_chooseLanguageFragment)
                }
            }
        }, 3000)


    }


    private fun onBoarding(): Boolean {
        val sharedPref = context?.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref!!.getBoolean("Finished", false)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }




}