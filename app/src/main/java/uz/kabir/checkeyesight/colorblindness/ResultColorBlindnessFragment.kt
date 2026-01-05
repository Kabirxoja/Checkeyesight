package uz.kabir.checkeyesight.colorblindness

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentResultColorBlindnessBinding

class ResultColorBlindnessFragment : Fragment() {

    private var _binding: FragmentResultColorBlindnessBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultColorBlindnessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}