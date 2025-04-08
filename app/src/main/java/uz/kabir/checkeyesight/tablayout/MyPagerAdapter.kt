package uz.kabir.checkeyesight.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_TABS = 2

class MyPagerAdapter(fragmentManager: FragmentManager, lifecycleTab: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycleTab) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return FirstTabFragment()
            }
        }
        return SecondTabFragment()
    }
}