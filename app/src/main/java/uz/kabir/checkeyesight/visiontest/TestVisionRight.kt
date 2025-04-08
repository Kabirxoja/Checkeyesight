package uz.kabir.checkeyesight.visiontest

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentRightVisionTestBinding
import uz.kabir.checkeyesight.db.User
import uz.kabir.checkeyesight.db.UserDatabase
import java.text.SimpleDateFormat
import java.util.*

class TestVisionRight : Fragment() {

    private var viewBinding: FragmentRightVisionTestBinding? = null
    private val binding get() = viewBinding!!

    private var mCurrentPosition: Int = 1

    private var mQuestionsList: ArrayList<Question>? = null //not initialised
    private var mSelectedOptionPosition: Int = 0


    private lateinit var options: List<Int?>

    private lateinit var tvOptionOne: ImageButton
    private lateinit var tvOptionTwo: ImageButton
    private lateinit var tvOptionThree: ImageButton
    private lateinit var tvOptionFour: ImageButton

    private var counter: Int = 0

    private lateinit var timer: CountDownTimer

    lateinit var dateTime: String
    lateinit var calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat


    private var questionString = ""
    private var rightEyeCount: Float = 1.0F
    private var leftEyeCount: Float = 1.0F

    private var sizeImage: Float = 0F
    private var distance = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvOptionOne = binding.tvOptionOne
        tvOptionTwo = binding.tvOptionTwo
        tvOptionThree = binding.tvOptionThree
        tvOptionFour = binding.tvOptionFour
        distance = arguments?.getInt("chooseDistance")!!

        when (distance) {
            1 -> {
                sizeImage = 5.82F
                resultText(rightEyeCount, sizeImage, "0,4")
                setImage(convertSizeImage(sizeImage))
            }
            2 -> {
                sizeImage = 29.1F
                resultText(rightEyeCount, sizeImage, "2")
                setImage(convertSizeImage(sizeImage))
            }
            3 -> {
                sizeImage = 43.65F
                resultText(rightEyeCount, sizeImage, "3")
                setImage(convertSizeImage(sizeImage))
            }
            4 -> {
                sizeImage = 58.2F
                resultText(rightEyeCount, sizeImage, "4")
                setImage(convertSizeImage(sizeImage))
            }
        }

        when (arguments?.getInt("positionSend")) {
            2 -> {
                mQuestionsList = List1.getQuestions1()
            }
            3 -> {
                mQuestionsList = List2.getQuestions2()
            }
        }


        tvOptionOne.setOnClickListener {
            mSelectedOptionPosition = 1
            selectQuestion(mSelectedOptionPosition)
        }
        tvOptionTwo.setOnClickListener {
            mSelectedOptionPosition = 2
            selectQuestion(mSelectedOptionPosition)
        }
        tvOptionThree.setOnClickListener {
            mSelectedOptionPosition = 3
            selectQuestion(mSelectedOptionPosition)
        }
        tvOptionFour.setOnClickListener {
            mSelectedOptionPosition = 4
            selectQuestion(mSelectedOptionPosition)
        }

        if (mCurrentPosition < 10) {
            setQuestion(mCurrentPosition)
        } else {
            findNavController().navigate(R.id.action_leftVisionTest_to_closingRightEye)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentRightVisionTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setQuestion(position: Int) {
        val question = mQuestionsList!![position - 1]
        //assign the progress bar value
        binding.progressBar.progress = position
        binding.tvProgress.text = "$position" + "/" + binding.progressBar.max
        //set image
        binding.ivImage.setImageResource(question.image)
        options = question.variants
        tvOptionOne.setBackgroundResource(options[0]!!)
        tvOptionTwo.setBackgroundResource(options[1]!!)
        tvOptionThree.setBackgroundResource(options[2]!!)
        tvOptionFour.setBackgroundResource(options[3]!!)

        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.ivImage.visibility = View.VISIBLE
                binding.timerLeft.visibility = View.VISIBLE
                binding.resultText.visibility = View.VISIBLE
                tvOptionOne.visibility = View.GONE
                tvOptionTwo.visibility = View.GONE
                tvOptionThree.visibility = View.GONE
                tvOptionFour.visibility = View.GONE
                binding.timerLeft.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding.ivImage.visibility = View.GONE
                binding.timerLeft.visibility = View.GONE
                binding.resultText.visibility = View.GONE
                tvOptionOne.visibility = View.VISIBLE
                tvOptionTwo.visibility = View.VISIBLE
                tvOptionThree.visibility = View.VISIBLE
                tvOptionFour.visibility = View.VISIBLE

            }
        }
        timer.start()

        binding.buttonNext.setOnClickListener {
            binding.ivImage.visibility = View.GONE
            tvOptionOne.visibility = View.VISIBLE
            tvOptionTwo.visibility = View.VISIBLE
            tvOptionThree.visibility = View.VISIBLE
            tvOptionFour.visibility = View.VISIBLE
            binding.buttonNext.visibility = View.GONE
            timer.cancel()
        }
        binding.buttonNext.visibility = View.VISIBLE

        binding.resultText.text = questionString
        binding.resultText.setTextColor(resources.getColor(R.color.pale_color))
    }

    @SuppressLint("SimpleDateFormat")
    private fun selectQuestion(select: Int) { //savolni tanlash
        Log.i("app1", rightEyeCount.toString())
        Log.i("app2", rightEyeCount.toString())


        mSelectedOptionPosition = select // 1,2,3,4
        val question = mQuestionsList!![mCurrentPosition - 1]

        Log.v("QUESTIONS -----------> ", question.image.toString())
        Log.v("OPTIONS>SELECTED ----> ", options[mSelectedOptionPosition - 1].toString())
        Log.v("Question        -----> ", question.toString())

        if (question.image == options[mSelectedOptionPosition - 1]) {
            counter += 2//increase count of correct answers
            when (distance) {
                1 -> resizeImages1(counter)
                2 -> resizeImages2(counter)
                3 -> resizeImages3(counter)
                4 -> resizeImages4(counter)
            }
        } else {
            counter -= 1
            when (distance) {
                1 -> resizeImages1(counter)
                2 -> resizeImages2(counter)
                3 -> resizeImages3(counter)
                4 -> resizeImages4(counter)
            }
        }

        mCurrentPosition++



        if (mCurrentPosition <= mQuestionsList!!.size) {
            setQuestion(mCurrentPosition)
        } else {
            //    findNavController().navigate(R.id.action_rightVisionTest_to_resultScreen)
            // NavHostFragment.create(R.navigation.my_navgraph, bundle)

            calendar = Calendar.getInstance()
            simpleDateFormat = SimpleDateFormat("dd/MM/yy HH:mm")
            dateTime = simpleDateFormat.format(calendar.time).toString()


            val bundle = Bundle()
            leftEyeCount = arguments?.getFloat("leftEyesCount")!!
            bundle.putFloat("left", leftEyeCount)
            bundle.putFloat("right", rightEyeCount)

            val database = UserDatabase.initDatabase(requireContext())
            database.userDao()
                .insertUser(User(0, leftEyeCount.toString(), rightEyeCount.toString(), dateTime))

            findNavController().navigate(R.id.action_rightVisionTest_to_resultScreen, bundle)
        }
    }


    private fun resizeImages1(counter: Int) {
        if (0 > counter) {
            rightEyeCount = 1.3F
            sizeImage = 5.82F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 0..1) {
            rightEyeCount = 1.0F
            sizeImage = 5.82F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 2..3) {
            rightEyeCount = 0.9F
            sizeImage = 4.621F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 4..5) {
            rightEyeCount = 0.8F
            sizeImage = 3.671F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 6..7) {
            rightEyeCount = 0.7F
            sizeImage = 2.916F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 8..9) {
            rightEyeCount = 0.6F
            sizeImage = 2.316F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 10..11) {
            rightEyeCount = 0.5F
            sizeImage = 1.84F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 12..13) {
            rightEyeCount = 0.4F
            sizeImage = 1.461F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 14..15) {
            rightEyeCount = 0.3F
            sizeImage = 1.161F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 16..17) {
            rightEyeCount = 0.2F
            sizeImage = 0.922F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 18..19) {
            rightEyeCount = 0.1F
            sizeImage = 0.733F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 20..21) {
            rightEyeCount = 0.0F
            sizeImage = 0.582F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 22..23) {
            rightEyeCount = -0.1F
            sizeImage = 0.462F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 24..25) {
            rightEyeCount = -0.2F
            sizeImage = 0.368F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 26..27) {
            rightEyeCount = -0.3F
            sizeImage = 0.368F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }

    }

    private fun resizeImages2(counter: Int) {
        if (0 > counter) {
            rightEyeCount = 1.3F
            sizeImage = 29.1F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 0..1) {
            rightEyeCount = 1.0F
            sizeImage = 29.1F
            setImage(convertSizeImage(sizeImage))
            Log.i("muhim", convertSizeImage(sizeImage).toString())
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 2..3) {
            rightEyeCount = 0.9F
            sizeImage = 23.105F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 4..5) {
            rightEyeCount = 0.8F
            sizeImage = 18.355F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 6..7) {
            rightEyeCount = 0.7F
            sizeImage = 14.58F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 8..9) {
            rightEyeCount = 0.6F
            sizeImage = 11.58F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 10..11) {
            rightEyeCount = 0.5F
            sizeImage = 9.2F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 12..13) {
            rightEyeCount = 0.4F
            sizeImage = 7.305F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 14..15) {
            rightEyeCount = 0.3F
            sizeImage = 5.805F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 16..17) {
            rightEyeCount = 0.2F
            sizeImage = 4.61F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 18..19) {
            rightEyeCount = 0.1F
            sizeImage = 3.665F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 20..21) {
            rightEyeCount = 0.0F
            sizeImage = 2.91F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 22..23) {
            rightEyeCount = -0.1F
            sizeImage = 2.31F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 24..25) {
            rightEyeCount = -0.2F
            sizeImage = 1.84F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }
        if (counter in 26..27) {
            rightEyeCount = -0.3F
            sizeImage = 1.46F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "2")
        }

    }

    private fun resizeImages3(counter: Int) {
        if (0 > counter) {
            rightEyeCount = 1.3F
            sizeImage = 43.65F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 0..1) {
            rightEyeCount = 1.0F
            sizeImage = 43.65F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 2..3) {
            rightEyeCount = 0.9F
            sizeImage = 34.6575F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 4..5) {
            rightEyeCount = 0.8F
            sizeImage = 27.5325F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 6..7) {
            rightEyeCount = 0.7F
            sizeImage = 21.87F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 8..9) {
            rightEyeCount = 0.6F
            sizeImage = 17.37F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 10..11) {
            rightEyeCount = 0.5F
            sizeImage = 13.8F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 12..13) {
            rightEyeCount = 0.4F
            sizeImage = 10.9575F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 14..15) {
            rightEyeCount = 0.3F
            sizeImage = 8.708F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 16..17) {
            rightEyeCount = 0.2F
            sizeImage = 6.915F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 18..19) {
            rightEyeCount = 0.1F
            sizeImage = 5.4975F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 20..21) {
            rightEyeCount = 0.0F
            sizeImage = 4.365F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }

        if (counter in 22..23) {
            rightEyeCount = -0.1F
            sizeImage = 3.465F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 24..25) {
            rightEyeCount = -0.2F
            sizeImage = 2.76F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }
        if (counter in 26..27) {
            rightEyeCount = -0.3F
            sizeImage = 2.19F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "3")
        }

    }

    private fun resizeImages4(counter: Int) {
        if (0 > counter) {
            rightEyeCount = 1.3F
            sizeImage = 58.2F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 0..1) {
            rightEyeCount = 1.0F
            sizeImage = 58.2F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 2..3) {
            rightEyeCount = 0.9F
            sizeImage = 46.21F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 4..5) {
            rightEyeCount = 0.8F
            sizeImage = 36.71F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 6..7) {
            rightEyeCount = 0.7F
            sizeImage = 29.16F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 8..9) {
            rightEyeCount = 0.6F
            sizeImage = 23.16F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 10..11) {
            rightEyeCount = 0.5F
            sizeImage = 18.4F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 12..13) {
            rightEyeCount = 0.4F
            sizeImage = 14.61F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 14..15) {
            rightEyeCount = 0.3F
            sizeImage = 11.61F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 16..17) {
            rightEyeCount = 0.2F
            sizeImage = 9.22F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 18..19) {
            rightEyeCount = 0.1F
            sizeImage = 7.33F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 20..21) {
            rightEyeCount = 0.0F
            sizeImage = 5.82F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }
        if (counter in 22..23) {
            rightEyeCount = -0.1F
            sizeImage = 4.62F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4")
        }

        if (counter in 24..25) {
            rightEyeCount = -0.2F
            sizeImage = 3.68F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }
        if (counter in 26..27) {
            rightEyeCount = -0.3F
            sizeImage = 2.92F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "0,4")
        }

    }

    private fun setImage(sizeImage: Float) {
        binding.ivImage.layoutParams.width = sizeImage.toInt()
        binding.ivImage.layoutParams.height = sizeImage.toInt()
    }

    private fun convertSizeImage(currentSizeImageMillimeters: Float): Float {
        val r: Resources = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_MM,
            currentSizeImageMillimeters,
            r.displayMetrics
        )
    }

    private fun resultText(resultLeftEye: Float, sizeImage: Float, distance: String) {
        questionString =
            "LogMar: " + resultLeftEye.toString() + "\n" +
                    "sizeImage: " + sizeImage.toString() + "\n" +
                    "distance: " + distance
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}