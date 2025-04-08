package uz.kabir.checkeyesight.colorblindness

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentResultColorBlindnessBinding

class ResultColorBlindness : Fragment() {


    private var viewBinding: FragmentResultColorBlindnessBinding? = null
    private val binding get() = viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentResultColorBlindnessBinding.inflate(inflater, container, false)
        val view = binding.root

        val result = arguments?.getInt("resultColorBlindness")!!

        if (result == 5) {
            binding.finalResult.text = getString(R.string.color_blindness_not_detected)
            binding.imageView2.setImageResource(R.drawable.icon_smile)
        } else {
            binding.finalResult.text = getString(R.string.color_blindness_detected)
            binding.imageView2.setImageResource(R.drawable.icon_sad)
        }

        binding.toHome.setOnClickListener {
            view.findNavController().navigate(R.id.homeFragment)
        }

        return view
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

}