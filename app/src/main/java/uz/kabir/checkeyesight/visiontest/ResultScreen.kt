package uz.kabir.checkeyesight.visiontest

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentResultScreenBinding
import uz.kabir.checkeyesight.main.MainActivity
import kotlin.Boolean
import kotlin.Int
import kotlin.math.abs


class ResultScreen : Fragment() {

    private var viewBinding: FragmentResultScreenBinding? = null
    private val binding get() = viewBinding!!
    private var progressing = 0
    private val imageIds = intArrayOf(
        R.drawable.step_1,
        R.drawable.step_2,
        R.drawable.step_3,
        R.drawable.step_4,
        R.drawable.step_5,
        R.drawable.step_6,
        R.drawable.step_7,
        R.drawable.step_8,
        R.drawable.step_9,
        R.drawable.step_10,
        R.drawable.step_11,
        R.drawable.step_12,
        R.drawable.step_13,
        R.drawable.step_14


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentResultScreenBinding.inflate(inflater, container, false)

        val leftEyeResult = arguments?.getFloat("left", 0F)!!
        val rightEyeResult = arguments?.getFloat("right", 0F)!!

        binding.rightResult.text = rightEyeResult.toString()
        binding.leftResult.text = leftEyeResult.toString()

        val finalResult = (abs(leftEyeResult) + abs(rightEyeResult)) / 2

        if (0.0F < finalResult && finalResult <= 0.09F) {
            seekBarImageShow(0)
        } else if (finalResult in 0.1F..0.15F) {
            seekBarImageShow(1)
        } else if (0.15F < finalResult && finalResult <= 0.25F) {
            seekBarImageShow(2)
        } else if (0.25F < finalResult && finalResult <= 0.35F) {
            seekBarImageShow(3)
        } else if (0.35F < finalResult && finalResult <= 0.45F) {
            seekBarImageShow(4)
        } else if (0.45F < finalResult && finalResult <= 0.55F) {
            seekBarImageShow(5)
        } else if (0.55F < finalResult && finalResult <= 0.65F) {
            seekBarImageShow(6)
        } else if (0.65F < finalResult && finalResult <= 0.75F) {
            seekBarImageShow(7)
        } else if (0.75F < finalResult && finalResult <= 0.85F) {
            seekBarImageShow(8)
        } else if (0.85F < finalResult && finalResult <= 0.95F) {
            seekBarImageShow(9)
        } else if (0.95F < finalResult && finalResult <= 1.05F) {
            seekBarImageShow(10)
        } else if (1.05F < finalResult && finalResult <= 1.15F) {
            seekBarImageShow(11)
        } else if (1.15F < finalResult && finalResult <= 1.25F) {
            seekBarImageShow(12)
        } else if (1.25F < finalResult && finalResult <= 1.35F) {
            seekBarImageShow(13)
        }


        binding.toHistory.setOnClickListener{
            view?.findNavController()?.navigate(R.id.viewResult)
        }

        binding.toHome.setOnClickListener {
            view?.findNavController()?.navigate(R.id.homeFragment)
            (activity as MainActivity).supportActionBar?.show()
        }




        binding.seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    binding.imageView.setImageResource(imageIds[progress])
                    textSeekBar(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })

        return binding.root
    }

    private fun seekBarImageShow(resultValue: Int) {
        binding.seekBar.progress = resultValue
        binding.imageView.setImageResource(imageIds[resultValue])
        textSeekBar(resultValue)
    }

    private fun textSeekBar(progress: Int) {
        val x: Int = (progress / 10)
        val y: Int = (progress % 10)
        binding.textView.text = "$x,$y"
    }

}