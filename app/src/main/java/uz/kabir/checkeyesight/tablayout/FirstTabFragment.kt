package uz.kabir.checkeyesight.tablayout

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentFirstTabBinding
import uz.kabir.checkeyesight.home.HomeFragmentDirections


class FirstTabFragment : Fragment(), AdapterView.OnItemClickListener,
    RecyclerTab1.OnItemClickedListener {

    private lateinit var photoAdapter: RecyclerTab1
    private var dataList = mutableListOf<DataModelTab>()

    private var viewBinding: FragmentFirstTabBinding? = null
    private val binding get() = viewBinding!!


    private val navController by lazy(LazyThreadSafetyMode.NONE) { view?.findNavController() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentFirstTabBinding.inflate(inflater, container, false)
        val view = binding.root

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            binding.recyclerViewTab1.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            // In portrait
            binding.recyclerViewTab1.layoutManager = GridLayoutManager(requireContext(), 1)
        }
        photoAdapter = RecyclerTab1(requireContext())
        binding.recyclerViewTab1.adapter = photoAdapter
        photoAdapter.setOnClickListener(this)

        //add data
        dataList.add(
            DataModelTab(
                getString(R.string.title1),
                R.drawable.item_e_chart
            )
        )
        dataList.add(
            DataModelTab(
                getString(R.string.title2),
                R.drawable.item_c_chart
            )
        )
        dataList.add(
            DataModelTab(
                getString(R.string.title3),
                R.drawable.item_snellen_test
            )
        )
        dataList.add(
            DataModelTab(
                getString(R.string.title4),
                R.drawable.item_number_test
            )
        )

        dataList.add(
            DataModelTab(
                getString(R.string.title8),
                R.drawable.item_bluetooth_test
            )
        )

        dataList.add(
            DataModelTab(
                getString(R.string.title5),
                R.drawable.item_colorblindness_test
            )
        )
        dataList.add(
            DataModelTab(
                getString(R.string.title6),
                R.drawable.item_duochrome_test
            )
        )
        dataList.add(
            DataModelTab(
                getString(R.string.title7),
                R.drawable.item_astigmatism
            )
        )
        dataList.add(
            DataModelTab(
                getString(R.string.title9),
                R.drawable.item_amslergrid_test
            )
        )
        dataList.add(
            DataModelTab(
                getString(R.string.title10),
                R.drawable.item_near_vision
            )
        )
        dataList.add(
            DataModelTab(
                getString(R.string.title11),
                R.drawable.item_contrast
            )
        )

        photoAdapter.setDataList(dataList)

        return view
    }

    override fun onClicked(position: Int) {

        val bundle = Bundle()
        bundle.putInt("position", position)

        if (position < 4) {
            navController?.navigate(
                HomeFragmentDirections.actionHomeFragmentToChooseDistance(
                    position
                )
            )
        } else if (position == 4) {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToChoosingConnectionFragment())
        } else if (position == 5) {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToColorBlindnessTest())
        } else if (position == 6) {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToDuochromeTest())
        } else if (position == 7) {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToLeftEyeTest())
        } else if (position == 8) {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToAmslerGrid())
        } else if (position == 9) {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToNearVisionTest())
        } else if (position == 10) {
            navController?.navigate(HomeFragmentDirections.actionHomeFragmentToContrastVisionTest())
        }

    }

    override fun clickInfo(position: Int) {
        val builder = android.app.AlertDialog.Builder(context)
        val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_info, null)
        builder.setView(dialogView)

        val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_button)
        val start = dialogView.findViewById<Button>(R.id.dialog_start_button)

        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val infoTitle = dialogView.findViewById<TextView>(R.id.info_title)


        when (position) {
            0 -> {
                infoTitle.text = getString(R.string.info_tumbling_E_chart)
                dialogTitle.text = getString(R.string.info_tumbling_E_chart_headline)
            }

            1 -> {
                infoTitle.text = getString(R.string.info_landolt_C_chart)
                dialogTitle.text = getString(R.string.info_landolt_C_chart_headline)
            }

            2 -> {
                infoTitle.text = getString(R.string.info_snellen_chart)
                dialogTitle.text = getString(R.string.info_snellen_chart_headline)
            }

            3 -> {
                infoTitle.text = getString(R.string.info_snellen_number_chart)
                dialogTitle.text = getString(R.string.info_snellen_number_chart_headline)
            }

            4 -> {
                infoTitle.text = getString(R.string.info_bluetooth)
                dialogTitle.text =getString(R.string.title8)
            }

            5 -> {
                infoTitle.text = getString(R.string.info_color_blindness_test)
                dialogTitle.text = getString(R.string.info_color_blindness_dialog)
            }

            6 -> {
                infoTitle.text = getString(R.string.info_duochrome_test)
                dialogTitle.text = getString(R.string.info_duochrome_dialog)
            }

            7 -> {
                infoTitle.text = getString(R.string.info_astigmatism_test)
                dialogTitle.text = getString(R.string.info_astigmatism_dialog)
            }

            8 -> {
                infoTitle.text = getString(R.string.info_amslergrid_test)
                dialogTitle.text = getString(R.string.info_amslergrid_dialog)
            }

            9 -> {
                infoTitle.text = getString(R.string.info_near_vision_dialog)
                dialogTitle.text = getString(R.string.title10)
            }
            10 -> {
                infoTitle.text = getString(R.string.info_contrast)
                dialogTitle.text = getString(R.string.title11)
            }
        }


        val dialog = builder.create()
        dialog.show()

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        start.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("position", position)
            if (position < 4) {
                navController?.navigate(
                    HomeFragmentDirections.actionHomeFragmentToChooseDistance(
                        position
                    )
                )
                dialog.dismiss()
            } else if (position == 4) {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToChoosingConnectionFragment())
                dialog.dismiss()
            } else if (position == 5) {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToColorBlindnessTest())
                dialog.dismiss()
            } else if (position == 6) {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToDuochromeTest())
                dialog.dismiss()
            } else if (position == 7) {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToLeftEyeTest())
                dialog.dismiss()
            } else if (position == 8) {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToAmslerGrid())
                dialog.dismiss()
            } else if (position == 9) {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToNearVisionTest())
                dialog.dismiss()
            }

        }


        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewTab1.layoutManager = GridLayoutManager(requireContext(), 2)

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerViewTab1.layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

}