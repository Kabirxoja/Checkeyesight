package uz.kabir.checkeyesight.swipetest

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentSwipeTestBySymbolsBinding


class SwipeTestBySymbolsLeft : Fragment() {

    private lateinit var layout: ConstraintLayout
    private var currentPosition = 1
    private var questionList: ArrayList<Questions>? = null
    private var correctAnswer: Int = 0

    private var viewBinding: FragmentSwipeTestBySymbolsBinding? = null
    private val binding get() = viewBinding!!

    private var positionSend = 0

    private var questionString = ""
    private var sizeImage: Float = 0F
    private var leftEyesCount: Float = 1.0F

    private var distance = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentSwipeTestBySymbolsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout = binding.relativeLayout1

        distance = arguments?.getInt("chooseDistance")!!

        when(distance)
        {
            1->
            {
                sizeImage = 5.82F
                resultText(leftEyesCount,sizeImage,"0,4")
                setImage(convertSizeImage(sizeImage))
            }
            2->
            {
                sizeImage = 29.1F
                resultText(leftEyesCount,sizeImage,"2")
                setImage(convertSizeImage(sizeImage))
            }
            3->
            {
                sizeImage = 43.65F
                resultText(leftEyesCount,sizeImage,"3")
                setImage(convertSizeImage(sizeImage))
            }
            4->
            {
                sizeImage = 58.2F
                resultText(leftEyesCount,sizeImage,"4")
                setImage(convertSizeImage(sizeImage))
            }
        }


        when (arguments?.getInt("positionSend")) {
            0 -> {
                questionList = ListQuestions.getQuestionSymbolE()
            }
            1 -> {
                questionList = ListQuestions.getQuestionSymbolC()
            }
        }

        positionSend = arguments?.getInt("positionSend")!! //position

        layout.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                selectQuestion("left")
                vibratePhone()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                selectQuestion("right")
                vibratePhone()
            }

            override fun onSwipeUp() {
                super.onSwipeUp()
                selectQuestion("up")
                vibratePhone()
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
                selectQuestion("down")
                vibratePhone()
            }
        })
    }


    override fun onResume() {
        setQuestions()
        super.onResume()
    }


    private fun selectQuestion(selectPosition: String) {
        val listQuestion = questionList?.get(currentPosition - 1)
        Log.i("list number ", listQuestion.toString())
        if (listQuestion!!.answer == selectPosition) {
            if (currentPosition < questionList!!.size) {
                correctAnswer += 2
            }
            when(distance)
            {
                1->resizeImages1(correctAnswer)
                2->resizeImages2(correctAnswer)
                3->resizeImages3(correctAnswer)
                4->resizeImages4(correctAnswer)
            }
        } else {

            correctAnswer -= 1
            when(distance)
            {
                1->resizeImages1(correctAnswer)
                2->resizeImages2(correctAnswer)
                3->resizeImages3(correctAnswer)
                4->resizeImages4(correctAnswer)
            }
        }


            currentPosition++


        if (currentPosition <= questionList!!.size) {
            setQuestions()
        } else {
            //binding.resultText.text = correctAnswer.toString()


            val bundle = Bundle()
            bundle.putInt("positionSend", positionSend)
            bundle.putFloat("leftEyesCount", leftEyesCount)
            bundle.putInt("chooseDistance", distance)

            findNavController().navigate(R.id.action_swipeTestBySymbols_to_closingRightEye, bundle)
        }

    }


    private fun setQuestions() {
        val list = questionList?.get(currentPosition - 1)
        binding.imageView.setImageResource(list!!.imageQuestions)
        binding.resultText.text = questionString
        binding.resultText.setTextColor(resources.getColor(R.color.pale_color))
    }

    private fun resizeImages1(counter: Int) {
        if (0 > counter) {
            leftEyesCount = 1.3F
            sizeImage = 5.82F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 0..1) {
            leftEyesCount = 1.0F
            sizeImage = 5.82F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 2..3) {
            leftEyesCount = 0.9F
            sizeImage = 4.621F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 4..5) {
            leftEyesCount = 0.8F
            sizeImage = 3.671F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 6..7) {
            leftEyesCount = 0.7F
            sizeImage = 2.916F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 8..9) {
            leftEyesCount = 0.6F
            sizeImage = 2.316F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 10..11) {
            leftEyesCount = 0.5F
            sizeImage = 1.84F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 12..13) {
            leftEyesCount = 0.4F
            sizeImage = 1.461F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 14..15) {
            leftEyesCount = 0.3F
            sizeImage = 1.161F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 16..17) {
            leftEyesCount = 0.2F
            sizeImage = 0.922F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 18..19) {
            leftEyesCount = 0.1F
            sizeImage = 0.733F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 20..21) {
            leftEyesCount = 0.0F
            sizeImage = 0.582F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 22..23) {
            leftEyesCount = -0.1F
            sizeImage = 0.462F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 24..25) {
            leftEyesCount = -0.2F
            sizeImage = 0.368F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 26..27) {
            leftEyesCount = -0.3F
            sizeImage = 0.368F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }

    }
    private fun resizeImages2(counter: Int) {
        if (0 > counter) {
            leftEyesCount = 1.3F
            sizeImage = 29.1F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 0..1) {
            leftEyesCount = 1.0F
            sizeImage = 29.1F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 2..3) {
            leftEyesCount = 0.9F
            sizeImage = 23.105F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 4..5) {
            leftEyesCount = 0.8F
            sizeImage = 18.355F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 6..7) {
            leftEyesCount = 0.7F
            sizeImage = 14.58F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 8..9) {
            leftEyesCount = 0.6F
            sizeImage = 11.58F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 10..11) {
            leftEyesCount = 0.5F
            sizeImage = 9.2F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 12..13) {
            leftEyesCount = 0.4F
            sizeImage = 7.305F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 14..15) {
            leftEyesCount = 0.3F
            sizeImage = 5.805F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 16..17) {
            leftEyesCount = 0.2F
            sizeImage = 4.61F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 18..19) {
            leftEyesCount = 0.1F
            sizeImage = 3.665F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 20..21) {
            leftEyesCount = 0.0F
            sizeImage = 2.91F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 22..23) {
            leftEyesCount = -0.1F
            sizeImage = 2.31F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 24..25) {
            leftEyesCount = -0.2F
            sizeImage = 1.84F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }
        if (counter in 26..27) {
            leftEyesCount = -0.3F
            sizeImage = 1.46F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"2")
        }

    }
    private fun resizeImages3(counter: Int) {
        if (0 > counter) {
            leftEyesCount = 1.3F
            sizeImage = 43.65F
            setImage(convertSizeImage(sizeImage))
            Log.i("muhim", convertSizeImage(sizeImage).toString())
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 0..1) {
            leftEyesCount = 1.0F
            sizeImage = 43.65F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 2..3) {
            leftEyesCount = 0.9F
            sizeImage = 34.6575F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 4..5) {
            leftEyesCount = 0.8F
            sizeImage = 27.5325F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 6..7) {
            leftEyesCount = 0.7F
            sizeImage = 21.87F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 8..9) {
            leftEyesCount = 0.6F
            sizeImage = 17.37F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 10..11) {
            leftEyesCount = 0.5F
            sizeImage = 13.8F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 12..13) {
            leftEyesCount = 0.4F
            sizeImage = 10.9575F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 14..15) {
            leftEyesCount = 0.3F
            sizeImage = 8.708F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 16..17) {
            leftEyesCount = 0.2F
            sizeImage = 6.915F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 18..19) {
            leftEyesCount = 0.1F
            sizeImage = 5.4975F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 20..21) {
            leftEyesCount = 0.0F
            sizeImage = 4.365F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }

        if (counter in 22..23) {
            leftEyesCount = -0.1F
            sizeImage = 3.465F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 24..25) {
            leftEyesCount = -0.2F
            sizeImage = 2.76F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }
        if (counter in 26..27) {
            leftEyesCount = -0.3F
            sizeImage = 2.19F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"3")
        }

    }
    private fun resizeImages4(counter: Int) {
        if (0 > counter) {
            leftEyesCount = 1.3F
            sizeImage = 58.2F
            setImage(convertSizeImage(sizeImage))
            Log.i("muhim", convertSizeImage(sizeImage).toString())
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 0..1) {
            leftEyesCount = 1.0F
            sizeImage = 58.2F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 2..3) {
            leftEyesCount = 0.9F
            sizeImage = 46.21F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 4..5) {
            leftEyesCount = 0.8F
            sizeImage = 36.71F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 6..7) {
            leftEyesCount = 0.7F
            sizeImage = 29.16F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 8..9) {
            leftEyesCount = 0.6F
            sizeImage = 23.16F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 10..11) {
            leftEyesCount = 0.5F
            sizeImage = 18.4F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 12..13) {
            leftEyesCount = 0.4F
            sizeImage = 14.61F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 14..15) {
            leftEyesCount = 0.3F
            sizeImage = 11.61F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 16..17) {
            leftEyesCount = 0.2F
            sizeImage = 9.22F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 18..19) {
            leftEyesCount = 0.1F
            sizeImage = 7.33F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 20..21) {
            leftEyesCount = 0.0F
            sizeImage = 5.82F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }
        if (counter in 22..23) {
            leftEyesCount = -0.1F
            sizeImage = 4.62F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"4")
        }

        if (counter in 24..25) {
            leftEyesCount = -0.2F
            sizeImage = 3.68F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }
        if (counter in 26..27) {
            leftEyesCount = -0.3F
            sizeImage = 2.92F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyesCount,sizeImage,"0,4")
        }

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

    private fun convertSizeImage(currentSizeImageMillimeters: Float): Float {
        val r: Resources = resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM,
            currentSizeImageMillimeters,
            r.displayMetrics)
    }

    private fun setImage(sizeImage: Float) {
        binding.imageView.layoutParams.width = sizeImage.toInt()
        binding.imageView.layoutParams.height = sizeImage.toInt()
    }

    private fun resultText(resultLeftEye: Float, sizeImage: Float, distance:String){
        questionString =
                "LogMar: "    +   resultLeftEye.toString() + "\n" +
                "sizeImage: " +   sizeImage.toString()     + "\n" +
                "distance: "  +   distance
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

}
