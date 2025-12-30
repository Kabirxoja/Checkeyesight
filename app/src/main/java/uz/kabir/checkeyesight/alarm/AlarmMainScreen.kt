package uz.kabir.checkeyesight.alarm

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import uz.kabir.checkeyesight.databinding.CustomDialogAlarmBinding
import uz.kabir.checkeyesight.databinding.FragmentAlarmMainScreenBinding


class AlarmMainScreen : Fragment() {
    private var viewBinding: FragmentAlarmMainScreenBinding? = null
    private val binding get() = viewBinding!!

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private lateinit var alarmAdapter: AlarmAdapter
    private val alarmList = mutableListOf<AlarmEntity>()
    private var idCount = 0

    // Room
    private lateinit var database: AlarmDatabase
    private lateinit var dao: AlarmDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAlarmMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = AlarmDatabase.alarmDatabase(requireContext())
        dao = database.daoAlarm()

        alarmAdapter = AlarmAdapter(
            items = alarmList,
            onDelete = { alarmItem ->
                lifecycleScope.launch {
                    dao.deleteAlarm(alarmItem)
                }
            },
            onEdit = { alarmItem ->
                showAlarmDialog(alarmItem)
            }
        )
        binding.rvAlarm.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAlarm.adapter = alarmAdapter
        binding.btnAddAlarm.setOnClickListener {
            showAlarmDialog(null)
        }


        observeAlarms()
    }

    private fun observeAlarms() {
        lifecycleScope.launch {
            dao.getAllAlarms().collect { alarms ->
                alarmList.clear()
                alarmList.addAll(alarms)
                alarmAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showAlarmDialog(alarmItem: AlarmEntity?) {
        val dialogBinding = CustomDialogAlarmBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireActivity()).setView(dialogBinding.root).create()

        alarmItem?.let {
            dialogBinding.timePicker.hour = it.hour
            dialogBinding.timePicker.minute = it.minute
            dialogBinding.checkboxMonday.isChecked = it.days.contains(Calendar.MONDAY)
            dialogBinding.checkboxTuesday.isChecked = it.days.contains(Calendar.TUESDAY)
            dialogBinding.checkboxWednesday.isChecked = it.days.contains(Calendar.WEDNESDAY)
            dialogBinding.checkboxThursday.isChecked = it.days.contains(Calendar.THURSDAY)
            dialogBinding.checkboxFriday.isChecked = it.days.contains(Calendar.FRIDAY)
            dialogBinding.checkboxSaturday.isChecked = it.days.contains(Calendar.SATURDAY)
            dialogBinding.checkboxSunday.isChecked = it.days.contains(Calendar.SUNDAY)
        }

        dialogBinding.btnSetAlarm.setOnClickListener {
            val daysOfSetAlarm = mutableListOf<Int>()
            if (dialogBinding.checkboxMonday.isChecked) daysOfSetAlarm.add(Calendar.MONDAY)
            if (dialogBinding.checkboxMonday.isChecked) daysOfSetAlarm.add(Calendar.TUESDAY)
            if (dialogBinding.checkboxWednesday.isChecked) daysOfSetAlarm.add(Calendar.WEDNESDAY)
            if (dialogBinding.checkboxThursday.isChecked) daysOfSetAlarm.add(Calendar.THURSDAY)
            if (dialogBinding.checkboxThursday.isChecked) daysOfSetAlarm.add(Calendar.FRIDAY)
            if (dialogBinding.checkboxSaturday.isChecked) daysOfSetAlarm.add(Calendar.SATURDAY)
            if (dialogBinding.checkboxSunday.isChecked) daysOfSetAlarm.add(Calendar.SUNDAY)

            val hour = dialogBinding.timePicker.hour
            val minute = dialogBinding.timePicker.minute

            if (alarmItem == null) {
                val newAlarm = AlarmEntity(
                    hour = hour,
                    minute = minute,
                    days = daysOfSetAlarm
                )
                lifecycleScope.launch {
                    dao.insertAlarm(newAlarm)
                }
            } else {
                // Edit — copy() orqali yangi obyekt yaratamiz
                val updatedAlarm = alarmItem.copy(
                    hour = hour,
                    minute = minute,
                    days = daysOfSetAlarm.toList()  // yangi roʻyxat
                )
                lifecycleScope.launch {
                    dao.updateAlarm(updatedAlarm)
                }
            }
            alarmAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}