package uz.kabir.checkeyesight.astigmatism

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentLeftEyeTestBinding


class LeftEyeTestFragment : Fragment() {

    private var _binding: FragmentLeftEyeTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLeftEyeTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_leftEyeTest_to_astigmatismTest2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}