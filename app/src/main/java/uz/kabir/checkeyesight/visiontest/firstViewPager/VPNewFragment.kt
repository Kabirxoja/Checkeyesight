package uz.kabir.checkeyesight.visiontest.firstViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.kabir.checkeyesight.databinding.FragmentVPNewBinding
import uz.kabir.checkeyesight.linechart.showlog

class VPNewFragment : Fragment() {

    private var _binding: FragmentVPNewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentVPNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  p =  arguments?.getInt("position")

        GlobalFields.position = p
        GlobalFields.distance = arguments?.getInt("chooseDistance")
        "p = $p   global p  = ${GlobalFields.position}".showlog()

        val fragmentList = arrayListOf<Fragment>(
            TestScreenFirstFragment(),
            TestScreenSecondFragment(),
            TestScreenThirdFragment(),
        )
        val adapter = VPAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.vpNew.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}