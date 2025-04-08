package uz.kabir.checkeyesight.amslergrid

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
import uz.kabir.checkeyesight.databinding.FragmentAmslerGridBinding


class AmslerGrid : Fragment() {

    private lateinit var sharedPreference:SharedPreferences
    lateinit var editor:Editor

    private var viewBinding: FragmentAmslerGridBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentAmslerGridBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = context?.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)!!
        editor = sharedPreference.edit()

        if (!sharedPreference.getBoolean(Constants.DO_NOT_SHOW,false))
        {
            dialog()
        }




    }


    private fun dialog() {

        val builder = android.app.AlertDialog.Builder(context)
        val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_info, null)
        builder.setView(dialogView)

        val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_button)
        cancel.text = "don't mention"
        val start = dialogView.findViewById<Button>(R.id.dialog_start_button)
        start.text = "Ok"

        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val infoTitle = dialogView.findViewById<TextView>(R.id.info_title)
        infoTitle.setText(R.string.info_amslergrid_test)

        dialogTitle.text = getString(R.string.info_amslergrid_dialog)

        val dialog = builder.create()
        dialog.show()

        cancel.setOnClickListener {
            dialog.dismiss()


            editor.putBoolean(Constants.DO_NOT_SHOW,true)
            editor.commit()


        }
        start.setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
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
        viewBinding=null
        super.onDestroyView()
    }



}