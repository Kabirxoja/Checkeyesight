package uz.kabir.checkeyesight.visiontest.firstViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentTestScreenSecondBinding


class TestScreenSecondFragment : Fragment() {

    private var _binding: FragmentTestScreenSecondBinding? = null
    private val binding get() = _binding!!

    private var positionSelect: Int? = GlobalFields.position
    private var distance: Int? = GlobalFields.distance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTestScreenSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager1 = activity?.findViewById<ViewPager2>(R.id.vp_new)
        binding.vpBtn.setOnClickListener {
            viewPager1!!.currentItem = 2
        }

        val bundle = Bundle()
        bundle.putInt("positionSend", positionSelect!!)
        bundle.putInt("chooseDistance", distance!!)

        binding.skip.setOnClickListener {
            findNavController().navigate(R.id.action_VPNewFragment_to_closingLeftEye, bundle)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}