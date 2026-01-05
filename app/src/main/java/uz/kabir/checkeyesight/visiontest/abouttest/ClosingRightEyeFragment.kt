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


open class ClosingRightEyeFragment : Fragment() {

    private var _binding: FragmentClosingRightEyeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Back press lifecycle-safe
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosingRightEyeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val leftEyesCount = arguments?.getFloat("leftEyesCount") //8
        val positionSend = arguments?.getInt("positionSend") //4
        val distance = arguments?.getInt("chooseDistance")!!


        val bundle = Bundle().apply {
            this.putInt("positionSend", positionSend!!) //4
            this.putFloat("leftEyesCount", leftEyesCount!!) //8
            this.putInt("chooseDistance", distance) //1-4
        }


        // Click listener
        binding.vpBtn.setOnClickListener {
            when (positionSend) {
                0, 1 -> findNavController().navigate(
                    R.id.action_closingRightEye_to_swipeTestBySymbolsRight,
                    bundle
                )
                2, 3 -> findNavController().navigate(
                    R.id.action_closingRightEye_to_rightVisionTest,
                    bundle
                )
            }
        }
    }

    private fun showExitDialog() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_info, null)
        builder.setView(dialogView)

        val cancelBtn = dialogView.findViewById<Button>(R.id.dialog_cancel_button)
        val startBtn = dialogView.findViewById<Button>(R.id.dialog_start_button)
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val infoTitle = dialogView.findViewById<TextView>(R.id.info_title)

        infoTitle.visibility = View.GONE
        startBtn.text = getString(R.string.answer_yes)
        cancelBtn.text = getString(R.string.answer_no)
        dialogTitle.text = getString(R.string.close_test_question)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        cancelBtn.setOnClickListener { dialog.dismiss() }
        startBtn.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
