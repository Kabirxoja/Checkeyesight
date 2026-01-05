package uz.kabir.checkeyesight.amslergrid

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.CustomDialogInfoBinding
import uz.kabir.checkeyesight.databinding.FragmentAmslerGridBinding


class AmslerGridFragment : Fragment() {

    private var _binding: FragmentAmslerGridBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreference:SharedPreferences
    lateinit var editor:Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAmslerGridBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = context?.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)!!
        editor = sharedPreference.edit()

        if (!sharedPreference.getBoolean(Constants.DO_NOT_SHOW,false))
            amslerDialog()
    }


    private fun amslerDialog() {
        val binding = CustomDialogInfoBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
        with(binding) {
            dialogCancelButton.text = getString(R.string.dont_mention)
            dialogStartButton.text = getString(R.string.okay)
            infoTitle.setText(R.string.info_amslergrid_test)
            dialogTitle.setText(R.string.info_amslergrid_dialog)
            dialogCancelButton.setOnClickListener {
                saveDoNotShow()
                dialog.dismiss()
            }
            dialogStartButton.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun saveDoNotShow(){
        editor.putBoolean(Constants.DO_NOT_SHOW, true).apply()
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
        super.onDestroyView()
        _binding=null
    }
}