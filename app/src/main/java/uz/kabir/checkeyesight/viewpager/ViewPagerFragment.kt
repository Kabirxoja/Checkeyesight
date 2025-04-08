package uz.kabir.checkeyesight.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import uz.kabir.checkeyesight.databinding.FragmentViewPagerBinding
import uz.kabir.checkeyesight.viewpager.screen.FirstScreen
import uz.kabir.checkeyesight.viewpager.screen.FourthScreen
import uz.kabir.checkeyesight.viewpager.screen.SecondScreen
import uz.kabir.checkeyesight.viewpager.screen.ThirdScreen


class ViewPagerFragment : Fragment() {
    private var viewBinding: FragmentViewPagerBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root

        val fragmentList = arrayListOf(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
            FourthScreen()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        binding.viewPager1.adapter = adapter
        return view
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

}



