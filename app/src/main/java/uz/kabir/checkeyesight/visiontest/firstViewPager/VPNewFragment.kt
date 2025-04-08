package uz.kabir.checkeyesight.visiontest.firstViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.kabir.checkeyesight.databinding.FragmentVPNewBinding
import uz.kabir.checkeyesight.linechart.showlog

class VPNewFragment : Fragment() {

    private var viewBinding: FragmentVPNewBinding? = null
    private val binding get() = viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentVPNewBinding.inflate(inflater, container, false)
        val view = binding.root

        val  p =  arguments?.getInt("position")

        GlobalFields.position = p
        GlobalFields.distance = arguments?.getInt("chooseDistance")
        "p = $p   global p  = ${GlobalFields.position}".showlog()

        val fragmentList = arrayListOf<Fragment>(
            TestScreenFirst(),
            TestScreenSecond(),
            TestScreenThird(),
        )
        val adapter = VPAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        binding.vpNew.adapter = adapter

        return view
    }
}