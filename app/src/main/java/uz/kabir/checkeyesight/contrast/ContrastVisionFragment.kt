package uz.kabir.checkeyesight.contrast

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentContrastVisionTestBinding


class ContrastVisionFragment : Fragment() {


    private var _binding: FragmentContrastVisionTestBinding? = null
    private val binding get() = _binding!!

    private var timer: CountDownTimer? = null
    private var isViewCreated = false // Track if the view is created

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContrastVisionTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.img11.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 100%"
            timerContrast()
        }
        binding.img12.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 83,5%"
            timerContrast()

        }
        binding.img13.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 67,5%"
            timerContrast()

        }
        binding.img14.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 51,4%"
            timerContrast()
        }
        binding.img15.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 35,3%"
            timerContrast()
        }

        binding.img21.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 19,2%"
            timerContrast()
        }
        binding.img22.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 18,1%"
            timerContrast()
        }
        binding.img23.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 16,9%"
            timerContrast()
        }
        binding.img24.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 15,7%"
            timerContrast()
        }
        binding.img25.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 14,6%"
            timerContrast()
        }
        binding.img31.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 13,4%"
            timerContrast()
        }
        binding.img32.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 12,4%"
            timerContrast()
        }
        binding.img33.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 11,2%"
            timerContrast()
        }
        binding.img34.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 10,0%"
            timerContrast()
        }
        binding.img35.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 8,6%"
            timerContrast()
        }
        binding.img41.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 7,68%"
            timerContrast()
        }
        binding.img42.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 6,5%"
            timerContrast()
        }
        binding.img43.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 5,31%"
            timerContrast()
        }
        binding.img44.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 4,13%"
            timerContrast()
        }
        binding.img45.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 2,95%"
            timerContrast()
        }

        binding.img51.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,97%"
            timerContrast()
        }
        binding.img52.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,87%"
            timerContrast()
        }
        binding.img53.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,77%"
            timerContrast()
        }
        binding.img54.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,67%"
            timerContrast()

        }
        binding.img55.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,57%"
            timerContrast()
        }

        binding.img61.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,48%"
            timerContrast()
        }
        binding.img62.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,38%"
            timerContrast()

        }
        binding.img63.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,28%"
            timerContrast()
        }
        binding.img64.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,18%"
            timerContrast()
        }
        binding.img65.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,082%"
            timerContrast()
        }

        binding.img71.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,948%"
            timerContrast()
        }
        binding.img72.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,886%"
            timerContrast()
        }
        binding.img73.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,787%"
            timerContrast()
        }
        binding.img74.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,689%"
            timerContrast()
        }
        binding.img75.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,591%"
            timerContrast()
        }

        binding.img81.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,492%"
            timerContrast()
        }
        binding.img82.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,394%"
            timerContrast()
        }
        binding.img83.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,295%"
            timerContrast()
        }
        binding.img84.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,197%"
            timerContrast()
        }
        binding.img85.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,098%"
            timerContrast()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.removeItem(R.id.info_uz)
    }


    private fun timerContrast() {
        isViewCreated = true

        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                if (isViewCreated) { // Check if view is still created before accessing binding
                    binding.textResult.visibility = View.VISIBLE
                }
            }

            override fun onFinish() {
                if (isViewCreated) { // Crucial check here too
                    binding.textResult.visibility = View.INVISIBLE
                }
            }
        }
        timer?.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timer?.cancel()
        timer = null
        isViewCreated = false
    }
}