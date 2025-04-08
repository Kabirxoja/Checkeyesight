package uz.kabir.checkeyesight.colorblindness

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentColorBlindnessTestBinding


class ColorBlindnessTest : Fragment() {

    private var viewBinding : FragmentColorBlindnessTestBinding? = null
    private val binding get() = viewBinding!!

    private val stringBuilder = StringBuilder()
    private var value : String = ""

    private var currentPosition = 1
    private var questionList: ArrayList<QuestionColorBlindness>? = null
    private var correctAnswer: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentColorBlindnessTestBinding.inflate(inflater,container,false)
        val view = binding.root

        questionList = List.getQuestionColorBlindness()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setQuestions()

        binding.btnZero.setOnClickListener {
            value += "0"
            stringBuilder.append(value)
            binding.textResult.text = value
        }
        binding.btnOne.setOnClickListener {
            value += "1"
            stringBuilder.append(value)
            binding.textResult.text = value

        }
        binding.btnTwo.setOnClickListener {
            value += "2"
            stringBuilder.append(value)
            binding.textResult.setText(value)


        }
        binding.btnThree.setOnClickListener {
            value += "3"
            stringBuilder.append(value)
            binding.textResult.text = value


        }
        binding.btnFour.setOnClickListener {
            value += "4"
            stringBuilder.append(value)
            binding.textResult.text = value


        }
        binding.btnFive.setOnClickListener {
            value += "5"
            stringBuilder.append(value)
            binding.textResult.text = value


        }
        binding.btnSix.setOnClickListener {
            value += "6"
            stringBuilder.append(value)
            binding.textResult.text = value


        }
        binding.btnSeven.setOnClickListener {
            value += "7"
            stringBuilder.append(value)
            binding.textResult.text = value


        }
        binding.btnEight.setOnClickListener {
            value += "8"
            stringBuilder.append(value)
            binding.textResult.text = value


        }
        binding.btnNine.setOnClickListener {
            value += "9"
            stringBuilder.append(value)
            binding.textResult.text = value

        }

        if (binding.textResult.text.toString().isNotEmpty()) //qiymat bor bo'lishini tekshirish
        {
            val value: String = binding.textResult.text.toString()
            stringBuilder.append(value)
            binding.textResult.text = value
        }




        binding.btnDelete.setOnClickListener {
            value = binding.textResult.text.toString()
            if (value.isNotEmpty())
            {
                value = value.substring(0, value.length - 1)
                binding.textResult.text = value
                value = ""
            }

            if (stringBuilder.isNotEmpty())
            {
                stringBuilder.deleteCharAt(stringBuilder.length - 1)
                binding.textResult.setText(R.string.enter_text)
            }

        }

        binding.btnNext.setOnClickListener {
            if (value.isNotEmpty())
            {
                selectQuestion(value)
                value = ""
                binding.textResult.setText(R.string.enter_text)
            }
            else
                Toast.makeText(requireContext(), R.string.text_is_empty, Toast.LENGTH_SHORT).show()
        }

    }


    private fun selectQuestion(selectPosition: String) {

        val listQuestion = questionList?.get(currentPosition - 1)

        Log.i("list number ", listQuestion.toString())

        if (listQuestion!!.answer == selectPosition) {
            correctAnswer += 1
        }
        else
        {
        }

        currentPosition++

        if (currentPosition <= questionList!!.size) {
            setQuestions()
        } else {

            val bundle = Bundle()
            bundle.putInt("resultColorBlindness", correctAnswer)

            findNavController().navigate(R.id.action_colorBlindnessTest_to_resultColorBlindness, bundle)
        }

    }
    private fun setQuestions() {
        val list = questionList?.get(currentPosition - 1)
        binding.imageColorBlindness.setImageResource(list!!.imageQuestions)
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
        viewBinding=null
        super.onDestroyView()
    }
}