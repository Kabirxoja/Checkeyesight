package uz.kabir.checkeyesight.astigmatism

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.kabir.checkeyesight.databinding.FragmentRightEyeTestBinding


class RightEyeTest : Fragment() {

    private var viewBinding: FragmentRightEyeTestBinding? = null
    private val binding get() = viewBinding!!

    private val args: RightEyeTestArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentRightEyeTestBinding.inflate(inflater, container, false)


        val x = args.answer

        binding.vpBtn.setOnClickListener {
            val action = RightEyeTestDirections.actionRightEyeTestToAstigmatismTest(x)
            findNavController().navigate(action)
        }



        return binding.root
    }

}