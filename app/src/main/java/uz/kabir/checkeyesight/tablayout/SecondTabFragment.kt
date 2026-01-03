package uz.kabir.checkeyesight.tablayout


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentSecondTabBinding
import uz.kabir.checkeyesight.home.HomeFragmentDirections

class SecondTabFragment : Fragment(), AdapterView.OnItemClickListener,
    RecyclerTab2.OnItemClickedListener {

    private var viewBinding: FragmentSecondTabBinding? = null
    private val binding get() = viewBinding!!

    private lateinit var photoAdapter: RecyclerTab2
    private val navController by lazy(LazyThreadSafetyMode.NONE) { view?.findNavController() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentSecondTabBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orientation = resources.configuration
        orientationScreenState(orientation)

        photoAdapter = RecyclerTab2(requireContext())
        binding.recyclerViewTab2.adapter = photoAdapter
        photoAdapter.setOnClickListener(this)

        val dataList = mutableListOf<DataModelTab>(
            DataModelTab(
                getString(R.string.restoration_of_vision),
                R.drawable.eye_focus
            ),
            DataModelTab(
                getString(R.string.exercise_for_nearsightedness),
                R.drawable.eye_focus
            ),
            DataModelTab(
                getString(R.string.exercise_for_farsightedness),
                R.drawable.eye_focus
            ),
            DataModelTab(
                getString(R.string.relaxation_of_vision),
                R.drawable.eye_focus
            )

        )
        photoAdapter.setDataList(dataList)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onClicked(position: Int) {

        when (position) {
            0 -> {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToMainRecovery())
            }
            1 -> {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToMainNearsightedness())
            }

            2 -> {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToMainFarsightedness())
            }

            3 -> {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToMainRelaxation())
            }
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        orientationScreenState(newConfig)
    }

    private fun orientationScreenState(newConfig: Configuration) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewTab2.layoutManager = GridLayoutManager(requireContext(), 2)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerViewTab2.layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}