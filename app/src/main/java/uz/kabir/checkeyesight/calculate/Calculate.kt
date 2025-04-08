package uz.kabir.checkeyesight.calculate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentCalculateBinding


class Calculate : Fragment() {

    private var viewBinding: FragmentCalculateBinding? = null
    private val binding get() = viewBinding!!

    private var counter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentCalculateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFirst.setOnClickListener {
            findNavController().navigate(R.id.action_calculate_to_showResultGlass2)
        }


        binding.textResult.text = "0,0"

        binding.increase.setOnClickListener {

            if (counter < 13) {
                convertToGlasses(counter)
                counter += 1
                convertToGlasses(counter)
                val incResult = (counter / 10.0).toFloat()
                binding.textResult.text = String.format("%.2f", incResult)
            }

        }
        binding.decrease.setOnClickListener {
            convertToGlasses(counter)

            if (counter > 0) {
                counter -= 1
                convertToGlasses(counter)
                val decResult = (counter / 10.0).toFloat()
                binding.textResult.text = String.format("%.2f", decResult)
            }
        }

    }

    private fun convertToGlasses(logMar: Int) {
        when (logMar) {
            0 -> binding.textViewResult.text = "${getString(R.string.n_a)}"
            1 -> binding.textViewResult.text = "-0,25 ... -0,5"
            2 -> binding.textViewResult.text = "-0,5 ... -0,75"
            3 -> binding.textViewResult.text = "-1,0"
            4 -> binding.textViewResult.text = "-1,25"
            5 -> binding.textViewResult.text = "-1.5"
            6 -> binding.textViewResult.text = "-1,75"
            7 -> binding.textViewResult.text = "-2,0"
            8 -> binding.textViewResult.text = "-2,25 ... -2,50"
            9 -> binding.textViewResult.text = "-2,50 ... 2,75"
            10 -> binding.textViewResult.text = "-3,0"
            11 -> binding.textViewResult.text = "-3,50"
            12 -> binding.textViewResult.text = "-4,0"
            13 -> binding.textViewResult.text = "-4,5"
        }
    }
}