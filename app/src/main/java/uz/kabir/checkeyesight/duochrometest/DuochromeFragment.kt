package uz.kabir.checkeyesight.duochrometest

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentDuochromeTestBinding


class DuochromeFragment : Fragment() {

    private var _binding: FragmentDuochromeTestBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDuochromeTestBinding.inflate(inflater, container, false)
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
        super.onDestroyView()
        _binding = null
    }
}