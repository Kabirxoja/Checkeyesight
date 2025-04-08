package uz.kabir.checkeyesight.visiontest.abouttest

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentChooseDistanceBinding
import uz.kabir.checkeyesight.linechart.showlog


class ChooseDistance : Fragment() {

    private var viewBinding: FragmentChooseDistanceBinding? = null
    private val binding get() = viewBinding!!
    private var selectDistance: Int = 0

    private val args: ChooseDistanceArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentChooseDistanceBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.smallestDistance.setOnClickListener {
            binding.smallestDistance.setBackgroundResource(R.drawable.btn_select_light_dark)
            binding.smallDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.mediumDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.highDistance.setBackgroundResource(R.drawable.btn_background_selected)

            binding.highDistance.setTextColor(resources.getColor(R.color.white))
            binding.highDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.smallDistance.setTextColor(resources.getColor(R.color.white))
            binding.smallDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.smallestDistance.setTextColor(resources.getColor(R.color.dark_night))
            binding.smallestDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)

            binding.mediumDistance.setTextColor(resources.getColor(R.color.white))
            binding.mediumDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            selectDistance = 1
        }

        binding.smallDistance.setOnClickListener {
            binding.smallestDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.smallDistance.setBackgroundResource(R.drawable.btn_select_light_dark)
            binding.mediumDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.highDistance.setBackgroundResource(R.drawable.btn_background_selected)

            binding.highDistance.setTextColor(resources.getColor(R.color.white))
            binding.highDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.smallDistance.setTextColor(resources.getColor(R.color.dark_night))
            binding.smallDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)

            binding.smallestDistance.setTextColor(resources.getColor(R.color.white))
            binding.smallestDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.mediumDistance.setTextColor(resources.getColor(R.color.white))
            binding.mediumDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            selectDistance = 2
        }
        binding.mediumDistance.setOnClickListener {
            binding.smallestDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.smallDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.mediumDistance.setBackgroundResource(R.drawable.btn_select_light_dark)
            binding.highDistance.setBackgroundResource(R.drawable.btn_background_selected)

            binding.highDistance.setTextColor(resources.getColor(R.color.white))
            binding.highDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.smallDistance.setTextColor(resources.getColor(R.color.white))
            binding.smallDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.smallestDistance.setTextColor(resources.getColor(R.color.white))
            binding.smallestDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.mediumDistance.setTextColor(resources.getColor(R.color.dark_night))
            binding.mediumDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)

            selectDistance = 3
        }
        binding.highDistance.setOnClickListener {
            binding.smallestDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.smallDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.mediumDistance.setBackgroundResource(R.drawable.btn_background_selected)
            binding.highDistance.setBackgroundResource(R.drawable.btn_select_light_dark)

            binding.highDistance.setTextColor(resources.getColor(R.color.dark_night))
            binding.highDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)

            binding.smallDistance.setTextColor(resources.getColor(R.color.white))
            binding.smallDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.smallestDistance.setTextColor(resources.getColor(R.color.white))
            binding.smallestDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            binding.mediumDistance.setTextColor(resources.getColor(R.color.white))
            binding.mediumDistance.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)

            selectDistance = 4
        }

        val p = args.position
        "position in Choose Distance = $p".showlog()

        binding.vpBtn.setOnClickListener {

            if(selectDistance != 0) {
                val bundle = Bundle()
                bundle.putInt("chooseDistance", selectDistance)
                bundle.putInt("position", p)
                
                findNavController().navigate(R.id.action_chooseDistance_to_VPNewFragment, bundle)
            }
            else{
                Toast.makeText(context, getString(R.string.distance_is_empty), Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.removeItem(R.id.info_uz)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

}