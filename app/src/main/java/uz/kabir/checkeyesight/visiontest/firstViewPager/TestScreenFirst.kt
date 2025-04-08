package uz.kabir.checkeyesight.visiontest.firstViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentTestScreenFirstBinding


class TestScreenFirst : Fragment() {

    var viewBinding: FragmentTestScreenFirstBinding? = null
    val binding get() = viewBinding!!



    private var positionSelect: Int? = GlobalFields.position
    private var distance: Int? = GlobalFields.distance


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentTestScreenFirstBinding.inflate(inflater,container,false)



        val viewPager1 = activity?.findViewById<ViewPager2>(R.id.vp_new)
        binding.vpBtn.setOnClickListener {
            viewPager1!!.currentItem = 1
        }

        val bundle = Bundle()
        bundle.putInt("positionSend", positionSelect!!)
        bundle.putInt("chooseDistance", distance!!)

        binding.skip.setOnClickListener {
            findNavController().navigate(R.id.action_VPNewFragment_to_closingLeftEye,bundle)
        }

        binding.vpLottie.repeatCount = 2

        return binding.root
    }

}