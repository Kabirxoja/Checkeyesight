package uz.kabir.checkeyesight.visiontest.firstViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentTestScreenThirdBinding


class TestScreenThird : Fragment() {

    private var viewBinding: FragmentTestScreenThirdBinding? = null
    private val binding get() = viewBinding!!

    private var positionSelect: Int? = GlobalFields.position
    private var distance: Int? = GlobalFields.distance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentTestScreenThirdBinding.inflate(inflater, container, false)
        val view = binding.root

        val bundle = Bundle()
        bundle.putInt("positionSend", positionSelect!!)
        bundle.putInt("chooseDistance", distance!!)

        binding.skip.setOnClickListener {
            findNavController().navigate(R.id.action_VPNewFragment_to_closingLeftEye,bundle)
        }
        binding.vpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_VPNewFragment_to_closingLeftEye,bundle)
        }

        return view
    }



}