package uz.kabir.checkeyesight.duochrometest

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentDuochromeTestBinding


class DuochromeFragment : Fragment() {

    private var viewBinding: FragmentDuochromeTestBinding? = null
    private val binding get() = viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDuochromeTestBinding.inflate(inflater, container, false)
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
        super.onDestroyView()
    }
}