package uz.kabir.checkeyesight.astigmatism

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentResultAstigmatismBinding

class ResultAstigmatism : Fragment() {

    private var viewBinding: FragmentResultAstigmatismBinding? = null
    private val binding get() = viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentResultAstigmatismBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val leftEyeResult = arguments?.getInt("rltL")
        val rightEyeResult = arguments?.getInt("rltR")


        if (leftEyeResult == 4) {
            binding.leftResult.text = getString(R.string.has_no_astigmatism_left)
            binding.iconLeft.setImageResource(R.drawable.icon_smile)
        } else {
            binding.leftResult.text = getString(R.string.has_astigmatism_left)
            binding.iconLeft.setImageResource(R.drawable.icon_sad)

        }
        if (rightEyeResult == 4) {
            binding.rightResult.text = getString(R.string.has_no_astigmatism_right)
            binding.iconRight.setImageResource(R.drawable.icon_smile)
        } else {
            binding.rightResult.text = getString(R.string.has_astigmatism_right)
            binding.iconRight.setImageResource(R.drawable.icon_sad)
        }


        binding.toHome.setOnClickListener {
            view.findNavController().navigate(R.id.homeFragment)
        }
    }

    override fun onDestroyView() {
        viewBinding=null
        super.onDestroyView()
    }

}