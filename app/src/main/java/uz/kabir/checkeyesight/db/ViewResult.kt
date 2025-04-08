package uz.kabir.checkeyesight.db

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentViewResultBinding

class ViewResult : Fragment(), DataBaseRVA.OnUserClickedListener {

    private lateinit var list: MutableList<User>
    private lateinit var database: UserDatabase
    private lateinit var rvAdapter: DataBaseRVA

    private var viewBinding : FragmentViewResultBinding?=null
    private val binding get() = viewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentViewResultBinding.inflate(inflater,container,false)
        database = UserDatabase.initDatabase(requireContext())
        list = database.userDao().getAllUsers() as MutableList<User>
        rvAdapter = DataBaseRVA(list)
        list.reverse()
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding?.recyclerView?.addItemDecoration(itemDecoration)
        binding?.recyclerView?.adapter = rvAdapter
        rvAdapter.setOnUserClickedListener(this)






        return binding?.root
    }


    override fun onUserClicked(position: Int) {

        val builder = android.app.AlertDialog.Builder(context)
        val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_info, null)
        builder.setView(dialogView)

        val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_button)
        val start = dialogView.findViewById<Button>(R.id.dialog_start_button)

        start.setText(R.string.answer_yes)
        cancel.setText(R.string.answer_no)

        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val infoTitle = dialogView.findViewById<TextView>(R.id.info_title)
        infoTitle.visibility = View.GONE


        val action = ViewResultDirections


        dialogTitle.text = getString(R.string.delete_test_items)

        val dialog = builder.create()
        dialog.show()

        cancel.setOnClickListener {
            dialog.dismiss()
        }
        start.setOnClickListener {
            database.userDao().deleteUser(list[position])
            rvAdapter.deleteData(list[position])
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_menu, menu)
        menu.findItem(R.id.info_uz).isVisible = true
        menu.removeItem(R.id.info_uz)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info_uz -> {
                findNavController().navigate(R.id.action_viewResult_to_lineChartFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onDestroyView() {
        viewBinding=null
        super.onDestroyView()
    }



}