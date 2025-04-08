package uz.kabir.checkeyesight.visiontest.abouttest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentClosingRightEyeBinding


open class ClosingRightEye : Fragment() {

    private var viewBinding: FragmentClosingRightEyeBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val builder = android.app.AlertDialog.Builder(context)
                val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_info, null)
                builder.setView(dialogView)

                val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_button)
                val start = dialogView.findViewById<Button>(R.id.dialog_start_button)

                val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
                val infoTitle = dialogView.findViewById<TextView>(R.id.info_title)
                infoTitle.visibility = View.GONE

                start.text = getString(R.string.answer_yes)
                cancel.text = getString(R.string.answer_no)

                dialogTitle.text = getString(R.string.close_test_question)

                val dialog = builder.create()
                dialog.show()

                cancel.setOnClickListener {
                    dialog.dismiss()
                }
                start.setOnClickListener {
                    findNavController().navigate(R.id.homeFragment)
                    dialog.dismiss()
                }

                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentClosingRightEyeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val leftEyesCount = arguments?.getFloat("leftEyesCount") //8
        val positionSend = arguments?.getInt("positionSend") //4
        val distance = arguments?.getInt("chooseDistance")!!


        val bundle = Bundle()
        bundle.putInt("positionSend", positionSend!!) //4
        bundle.putFloat("leftEyesCount", leftEyesCount!!) //8
        bundle.putInt("chooseDistance", distance) //1-4


        binding.vpBtn.setOnClickListener {
            when (positionSend) {
                0 -> {
                    findNavController().navigate(
                        R.id.action_closingRightEye_to_swipeTestBySymbolsRight,
                        bundle
                    )

                }
                1 -> {
                    findNavController().navigate(
                        R.id.action_closingRightEye_to_swipeTestBySymbolsRight,
                        bundle
                    )

                }
                2 -> {
                    findNavController().navigate(
                        R.id.action_closingRightEye_to_rightVisionTest,
                        bundle
                    )
                }
                3 -> {
                    findNavController().navigate(
                        R.id.action_closingRightEye_to_rightVisionTest,
                        bundle
                    )
                }
            }
        }
    }
}