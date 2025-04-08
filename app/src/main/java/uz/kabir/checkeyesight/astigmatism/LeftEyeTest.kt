package uz.kabir.checkeyesight.astigmatism

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentLeftEyeTestBinding


class LeftEyeTest : Fragment() {

    private var viewBinding: FragmentLeftEyeTestBinding? = null
    private val binding get() = viewBinding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentLeftEyeTestBinding.inflate(inflater, container, false)

        binding.vpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_leftEyeTest_to_astigmatismTest2)
        }

        return binding.root
    }


}