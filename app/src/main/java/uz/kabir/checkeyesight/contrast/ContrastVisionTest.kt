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


class ContrastVisionTest : Fragment() {


    private var viewBinding: FragmentContrastVisionTestBinding? = null
    private val binding get() = viewBinding!!

    private var timer: CountDownTimer? = null
    private var isViewCreated = false // Track if the view is created

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentContrastVisionTestBinding.inflate(inflater, container, false)


        binding.img11.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 100%"
            timer()
        }
        binding.img12.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 83,5%"
            timer()

        }
        binding.img13.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 67,5%"
            timer()

        }
        binding.img14.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 51,4%"
            timer()
        }
        binding.img15.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 35,3%"
            timer()
        }

        binding.img21.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 19,2%"
            timer()
        }
        binding.img22.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 18,1%"
            timer()
        }
        binding.img23.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 16,9%"
            timer()
        }
        binding.img24.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 15,7%"
            timer()
        }
        binding.img25.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 14,6%"
            timer()
        }
        binding.img31.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 13,4%"
            timer()
        }
        binding.img32.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 12,4%"
            timer()
        }
        binding.img33.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 11,2%"
            timer()
        }
        binding.img34.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 10,0%"
            timer()
        }
        binding.img35.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 8,6%"
            timer()
        }
        binding.img41.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 7,68%"
            timer()
        }
        binding.img42.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 6,5%"
            timer()
        }
        binding.img43.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 5,31%"
            timer()
        }
        binding.img44.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 4,13%"
            timer()
        }
        binding.img45.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 2,95%"
            timer()
        }

        binding.img51.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,97%"
            timer()
        }
        binding.img52.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,87%"
            timer()
        }
        binding.img53.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,77%"
            timer()
        }
        binding.img54.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,67%"
            timer()

        }
        binding.img55.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,57%"
            timer()
        }

        binding.img61.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,48%"
            timer()
        }
        binding.img62.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,38%"
            timer()

        }
        binding.img63.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,28%"
            timer()
        }
        binding.img64.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,18%"
            timer()
        }
        binding.img65.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 1,082%"
            timer()
        }

        binding.img71.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,948%"
            timer()
        }
        binding.img72.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,886%"
            timer()
        }
        binding.img73.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,787%"
            timer()
        }
        binding.img74.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,689%"
            timer()
        }
        binding.img75.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,591%"
            timer()
        }

        binding.img81.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,492%"
            timer()
        }
        binding.img82.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,394%"
            timer()
        }
        binding.img83.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,295%"
            timer()
        }
        binding.img84.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,197%"
            timer()
        }
        binding.img85.setOnClickListener {
            binding.textResult.text = "${resources.getString(R.string.result)} 0,098%"
            timer()
        }




        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.removeItem(R.id.info_uz)
    }

    override fun onDestroyView() {
        viewBinding = null
        timer?.cancel()
        timer = null
        isViewCreated = false // Set to false when view is destroyed
        super.onDestroyView()
    }

    private fun timer() {
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


}