package uz.kabir.checkeyesight.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.kabir.checkeyesight.databinding.FragmentAlarmMainScreenBinding


class AlarmMainScreen : Fragment() {


    private var viewBinding: FragmentAlarmMainScreenBinding? = null
    private val binding get() = viewBinding!!

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAlarmMainScreenBinding.inflate(inflater, container, false)

        return binding.root
    }
}