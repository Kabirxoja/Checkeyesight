package uz.kabir.checkeyesight.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.kabir.checkeyesight.databinding.FragmentNoteScreenBinding

class NoteScreen : Fragment() {

    private var viewBinding: FragmentNoteScreenBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentNoteScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}