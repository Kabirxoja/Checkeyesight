package uz.kabir.checkeyesight.nearvisiontest


import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentNearVisionTestBinding
import uz.kabir.checkeyesight.swipetest.OnSwipeTouchListener


class NearVisionTest : Fragment() {

    var viewBinding: FragmentNearVisionTestBinding? = null
    val binding get() = viewBinding

    private lateinit var layout: ConstraintLayout
    private var sizeText: Float = 1.42f
    private var level = 0

    private lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    private var resultText = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentNearVisionTestBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = context?.getSharedPreferences("NearVisionTest", Context.MODE_PRIVATE)!!
        editor = sharedPreference.edit()

        if (!sharedPreference.getBoolean("doesNotShow", false)) {
            dialog()
        } else {
            rotateScreen()
        }
        sizeText = 14.2f
        resultText = "20/250"
        binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_250)
        convertSizeImage(sizeText, resultText)

        layout = binding?.layoutSwipe!!


        indicatorDots()
        changeDots(0)


        layout.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()

                if (level<12) {
                    level++
                }
                if (level in -1..15) {
                    Log.i("mylevel1", level.toString())
                    vibratePhone()
                    changeDots(level)
                    setAll(level)
                }


            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                if (level>0) {
                    level--
                }
                if (level in -1..15) {
                    Log.i("mylevel2", level.toString())
                    vibratePhone()
                    changeDots(level)
                    setAll(level)
                }


            }

            override fun onSwipeUp() {
                super.onSwipeUp()
                vibratePhone()
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                vibratePhone()
            }


        })


    }


    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        75,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                ) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(75) // Vibrate method for below API Level 26
            }
        }
    }


    private fun convertSizeImage(currentSizeImageMillimeters: Float, textResult: String) {
        binding?.mainTextTest?.setTextSize(TypedValue.COMPLEX_UNIT_MM, currentSizeImageMillimeters)
        binding?.result?.text = textResult
    }


    private fun setAll(levelTest: Int) {
        when (levelTest) {
            0 -> {
                /* level 0 - (20/250) */
                sizeText = 14.2f
                resultText = "20/250"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_250)
                convertSizeImage(sizeText, resultText)

            }
            1 -> {
                /* level 1 - (20/200) */
                sizeText = 12.9f
                resultText = "20/200"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_200)
                convertSizeImage(sizeText, resultText)
            }
            2 -> {
                /* level 2 - (20/160) */
                sizeText = 8.82f
                resultText = "20/160"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_160)
                convertSizeImage(sizeText, resultText)
            }
            3 -> {
                /* level 3 - (20/125) */
                sizeText = 7.06f
                resultText = "20/125"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_125)
                convertSizeImage(sizeText, resultText)
            }
            4 -> {
                /* level 4 - (20/100) */
                sizeText = 5.65f
                resultText = "20/100"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_100)
                convertSizeImage(sizeText, resultText)
            }
            5 -> {
                /* level 5 - (20/80) */
                sizeText = 4.41f
                resultText = "20/80"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_80)
                convertSizeImage(sizeText, resultText)
            }
            6 -> {
                /* level 6 - (20/63) */
                sizeText = 3.528f
                resultText = "20/63"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_63)
                convertSizeImage(sizeText, resultText)
            }
            7 -> {
                /* level 7 - (20/50) */
                sizeText = 2.83f
                resultText = "20/50"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_50)
                convertSizeImage(sizeText, resultText)
            }
            8 -> {
                /* level 8 - (20/40) */
                sizeText = 2.23f
                resultText = "20/40"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_40)
                convertSizeImage(sizeText, resultText)
            }
            9 -> {
                /* level 9 - (20/32) */
                sizeText = 1.764f
                resultText = "20/32"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_32)

                convertSizeImage(sizeText, resultText)
            }
            10 -> {
                /* level 10 - (20/25) */
                sizeText = 1.42f
                resultText = "20/25"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_25)
                convertSizeImage(sizeText, resultText)
            }
            11 -> {
                /* level 11 - (20/20) */
                sizeText = 1.129f
                resultText = "20/20"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_20)
                convertSizeImage(sizeText, resultText)
            }
            12 -> {
                /* level 12 - (20/16) */
                sizeText = 0.89f
                resultText = "20/16"
                binding?.mainTextTest?.text = resources.getString(R.string.near_vision_test_level_16)
                convertSizeImage(sizeText, resultText)
            }
            else -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun dialog() {

        val builder = android.app.AlertDialog.Builder(context)
        val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_duochrome, null)
        builder.setView(dialogView)

        val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_button)
        cancel.text = "don't mention"
        val start = dialogView.findViewById<Button>(R.id.dialog_start_button)
        start.text = "Ok"

        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        dialogTitle.text = getString(R.string.rotate_phone)

        val dialog = builder.create()
        dialog.show()

        cancel.setOnClickListener {
            dialog.dismiss()
            editor.putBoolean("doesNotShow", true)
            editor.commit()
            rotateScreen()
        }
        start.setOnClickListener {
            dialog.dismiss()
            rotateScreen()
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
    }

    private fun rotateScreen() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }


    private fun indicatorDots() {
        for (i in 0 until 13) {
            val indicator = ImageView(context)
            indicator.setImageResource(R.drawable.nonselecteditem_dot)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            indicator.layoutParams = params
            binding?.indicatorContainer?.addView(indicator)
        }
    }

    private fun changeDots(position: Int) {
        // Update the indicator to reflect the current position
        val childCount = binding?.indicatorContainer?.childCount
        for (i in 0 until childCount!!) {
            val indicator = binding?.indicatorContainer?.getChildAt(i) as ImageView
            if (i == position) {
                indicator.setImageResource(R.drawable.selecteditem_dot)
            } else {
                indicator.setImageResource(R.drawable.nonselecteditem_dot)
            }
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }



}