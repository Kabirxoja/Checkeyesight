package uz.kabir.checkeyesight.history;

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

class ViewResultFragment : Fragment(), HistoryAdapter.OnUserClickedListener {

    private var _binding: FragmentViewResultBinding? = null
    private val binding get() = _binding
    private lateinit var list: MutableList<HistoryEntity>
    private lateinit var database: UserDatabase
    private lateinit var rvAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentViewResultBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = UserDatabase.initDatabase(requireContext())
        list = database.userDao().getAllUsers() as MutableList<HistoryEntity>
        rvAdapter = HistoryAdapter(list)
        list.reverse()
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding?.recyclerView?.addItemDecoration(itemDecoration)
        binding?.recyclerView?.adapter = rvAdapter
        rvAdapter.setOnUserClickedListener(this)
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
        super.onDestroyView()
        _binding = null
    }
}