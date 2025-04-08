package uz.kabir.checkeyesight.astigmatism

import android.os.Bundle
import android.view.*
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentAstigmatismTestBinding
import uz.kabir.checkeyesight.swipetest.OnSwipeTouchListener


class AstigmatismTest : Fragment() {


    private var viewBinding: FragmentAstigmatismTestBinding? = null
    private val binding get() = viewBinding!!


    private var questionList: ArrayList<List.QuestionAstigmatism>? = null
    private var currentPosition = 1
    private var selected: Int = 0
    private var correct: Int = 0
    private lateinit var list: List.QuestionAstigmatism

    private val args: AstigmatismTestArgs by navArgs()

    private var valueEnter = -1
    private var leftEyeResult = 0
    private var swipeCount = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentAstigmatismTestBinding.inflate(inflater, container, false)
        val view = binding.root

        questionList = List.getListAstigmatism()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        visibleSecond()

        leftEyeResult = args.answersLeft
        if (leftEyeResult in 0..4) {
            valueEnter = 1
        }




        binding.swipeAstigmatismLayout.setOnTouchListener(object : OnSwipeTouchListener(context) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()

                if ((binding.btnYes.visibility == View.GONE)) {
                    visibleFirst()
                }

                swipeCount++

                if (selected == 0) {
                    if (swipeCount > 0) {
                        Toast.makeText(
                            requireContext(),
                            R.string.select_options,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                } else {

                    if (currentPosition > questionList!!.size) {

                        if (valueEnter == 1) {
                            val bundle = Bundle()
                            bundle.putInt("rltL", leftEyeResult)
                            bundle.putInt("rltR", correct)

                            findNavController().navigate(
                                R.id.action_astigmatismTest_to_resultAstigmatism,
                                bundle
                            )

                        } else {
                            val action =
                                AstigmatismTestDirections.actionAstigmatismTestToRightEyeTest(
                                    correct
                                )
                            findNavController().navigate(action)
                        }

                    }

                    if (list?.answer?.equals(selected.toString())!!) {
                        correct++
                        swipeCount = -1
                    }
                    currentPosition++
                    list = questionList?.get(currentPosition - 1)!!

                    selected = 0
                    binding.imageAstigmatism.setImageResource(list!!.imageQuestions)
                    binding.btnYes.setBackgroundResource(R.drawable.btn_background_unselected)
                    binding.btnNo.setBackgroundResource(R.drawable.btn_background_unselected)
                    binding.btnYes.setTextColor(resources.getColor(R.color.dark_night))
                    binding.btnNo.setTextColor(resources.getColor(R.color.dark_night))

                    visibleSecond2()

                }


            }


            override fun onSwipeRight() {
                super.onSwipeRight()
                if ((binding.btnYes.visibility == View.VISIBLE) && selected == 0) {
                    visibleSecond()
                    swipeCount = -1
                }
            }

        })

        if (currentPosition <= questionList!!.size) {
            list = questionList?.get(currentPosition - 1)!!
            binding.imageAstigmatism.setImageResource(list.imageQuestions)
        } else {

            if (valueEnter == 1) {
                val action = AstigmatismTestDirections.actionAstigmatismTestToResultAstigmatism()
                findNavController().navigate(action)
            } else {
                val action = AstigmatismTestDirections.actionAstigmatismTestToRightEyeTest(correct)
                findNavController().navigate(action)
            }
        }



        binding.btnYes.setOnClickListener {
            binding.btnYes.setBackgroundResource(R.drawable.btn_background_selected)
            binding.btnYes.setTextColor(resources.getColor(R.color.white))
            binding.btnNo.setTextColor(resources.getColor(R.color.dark_night))

            binding.btnNo.setBackgroundResource(R.drawable.btn_background_unselected)
            selected = 1
        }
        binding.btnNo.setOnClickListener {
            binding.btnYes.setBackgroundResource(R.drawable.btn_background_unselected)
            binding.btnNo.setBackgroundResource(R.drawable.btn_background_selected)
            binding.btnNo.setTextColor(resources.getColor(R.color.white))
            binding.btnYes.setTextColor(resources.getColor(R.color.dark_night))

            selected = 2
        }
    }


    private fun visibleSecond() {
        binding.btnYes.visibility = View.GONE
        binding.btnNo.visibility = View.GONE
        binding.questionText.visibility = View.GONE

        binding.imageAstigmatism.visibility = View.VISIBLE
        view?.slideOff(500)
    }

    private fun visibleSecond2() {
        binding.btnYes.visibility = View.GONE
        binding.btnNo.visibility = View.GONE
        binding.questionText.visibility = View.GONE

        binding.imageAstigmatism.visibility = View.VISIBLE
        view?.slideOff2(500)
    }

    private fun visibleFirst() {
        binding.btnYes.visibility = View.VISIBLE
        binding.btnNo.visibility = View.VISIBLE
        binding.questionText.visibility = View.VISIBLE

        binding.imageAstigmatism.visibility = View.GONE
        view?.slideOn(500)
    }

    private fun View.slideOn(duration: Int) {
        val animate = TranslateAnimation(this.height.toFloat(), 0f, 0f, 0f)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

    private fun View.slideOff(duration: Int) {
        val animate = TranslateAnimation(-this.height.toFloat(), 0f, 0f, 0f)
        animate.duration = duration.toLong()
        animate.fillBefore = true
        this.startAnimation(animate)
    }

    private fun View.slideOff2(duration: Int) {
        val animate = TranslateAnimation(this.height.toFloat(), 0f, 0f, 0f)
        animate.duration = duration.toLong()
        animate.fillBefore = true
        this.startAnimation(animate)
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