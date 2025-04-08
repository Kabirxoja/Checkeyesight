package uz.kabir.checkeyesight.viewpager.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentThirdScreenBinding

class ThirdScreen : Fragment() {

    private var viewBinding: FragmentThirdScreenBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager1)

        binding.thirdScreenButton.setOnClickListener {
            viewPager!!.currentItem = 3
        }
        binding.thirdTextSkip.setOnClickListener {
            viewPager!!.currentItem = 3
        }


        return view
    }



}