package uz.kabir.checkeyesight.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.custom.CustomPicker
import uz.kabir.checkeyesight.databinding.CustomDialogAlarmBinding
import uz.kabir.checkeyesight.databinding.FragmentAlarmMainScreenBinding


class AlarmMainScreen : Fragment() {
    private var viewBinding: FragmentAlarmMainScreenBinding? = null
    private val binding get() = viewBinding!!
    private lateinit var alarmAdapter: AlarmAdapter
    private val alarmList = mutableListOf<AlarmEntity>()
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
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // 🔥 CustomTimePicker yaratamiz
        val timePicker = CustomPicker(
            hourEt = dialogBinding.tvHour,
            minuteEt = dialogBinding.tvMinute
        )


        alarmItem?.let {
            Log.d("AlarmMainScreen", "alarmItem: $alarmItem")
            timePicker.setTime(it.hour, it.minute)
            dialogBinding.checkboxMonday.isChecked = it.days.contains(Calendar.MONDAY)
            dialogBinding.checkboxTuesday.isChecked = it.days.contains(Calendar.TUESDAY)
            dialogBinding.checkboxWednesday.isChecked = it.days.contains(Calendar.WEDNESDAY)
            dialogBinding.checkboxThursday.isChecked = it.days.contains(Calendar.THURSDAY)
            dialogBinding.checkboxFriday.isChecked = it.days.contains(Calendar.FRIDAY)
            dialogBinding.checkboxSaturday.isChecked = it.days.contains(Calendar.SATURDAY)
            dialogBinding.checkboxSunday.isChecked = it.days.contains(Calendar.SUNDAY)

//            Log.d("AlarmMainScreen",  "SUNDAY: ${it.days.contains(Calendar.SUNDAY)}")
//            Log.d("AlarmMainScreen", "SATURDAY: ${it.days.contains(Calendar.SATURDAY)}")
//            Log.d("AlarmMainScreen", "FRIDAY: ${it.days.contains(Calendar.FRIDAY)}")
//            Log.d("AlarmMainScreen", "THURSDAY: ${it.days.contains(Calendar.THURSDAY)}")
//            Log.d("AlarmMainScreen", "WEDNESDAY: ${it.days.contains(Calendar.WEDNESDAY)}")
//            Log.d("AlarmMainScreen", "TUESDAY: ${it.days.contains(Calendar.TUESDAY)}")
//            Log.d("AlarmMainScreen", "MONDAY: ${it.days.contains(Calendar.MONDAY)}")

        }

        dialogBinding.btnHourPlus.setOnClickListener { timePicker.incHour() }
        dialogBinding.btnHourMinus.setOnClickListener { timePicker.decHour() }
        dialogBinding.btnMinutePlus.setOnClickListener { timePicker.incMinute() }
        dialogBinding.btnMinuteMinus.setOnClickListener { timePicker.decMinute() }

        dialogBinding.btnSetAlarm.setOnClickListener {
            val daysOfSetAlarm = mutableListOf<Int>()
            if (dialogBinding.checkboxMonday.isChecked) daysOfSetAlarm.add(Calendar.MONDAY)
            if (dialogBinding.checkboxTuesday.isChecked) daysOfSetAlarm.add(Calendar.TUESDAY)
            if (dialogBinding.checkboxWednesday.isChecked) daysOfSetAlarm.add(Calendar.WEDNESDAY)
            if (dialogBinding.checkboxThursday.isChecked) daysOfSetAlarm.add(Calendar.THURSDAY)
            if (dialogBinding.checkboxFriday.isChecked) daysOfSetAlarm.add(Calendar.FRIDAY)
            if (dialogBinding.checkboxSaturday.isChecked) daysOfSetAlarm.add(Calendar.SATURDAY)
            if (dialogBinding.checkboxSunday.isChecked) daysOfSetAlarm.add(Calendar.SUNDAY)

//            Log.d("AlarmMainScreen", "MONDAY ADD: ${daysOfSetAlarm.add(Calendar.MONDAY)}")
//            Log.d("AlarmMainScreen", "TUESDAY ADD: ${daysOfSetAlarm.add(Calendar.TUESDAY)}")
//            Log.d("AlarmMainScreen", "WEDNESDAY ADD: ${daysOfSetAlarm.add(Calendar.WEDNESDAY)}")
//            Log.d("AlarmMainScreen", "THURSDAY ADD: ${daysOfSetAlarm.add(Calendar.THURSDAY)}")
//            Log.d("AlarmMainScreen", "FRIDAY ADD: ${daysOfSetAlarm.add(Calendar.FRIDAY)}")
//            Log.d("AlarmMainScreen", "SATURDAY ADD: ${daysOfSetAlarm.add(Calendar.SATURDAY)}")
//            Log.d("AlarmMainScreen", "SUNDAY ADD: ${daysOfSetAlarm.add(Calendar.SUNDAY)}")

            val (hour, minute) = timePicker.getTime()

            Log.d("timePicker", "hour: ${hour}")
            Log.d("timePicker", "hour: ${minute}")


            if (alarmItem == null) {
                val newAlarm = AlarmEntity(
                    hour = hour,
                    minute = minute,
                    days = daysOfSetAlarm
                )
                lifecycleScope.launch {
                    dao.insertAlarm(newAlarm)
                }
                Log.d("alarmItem", "alarmItem == null: hour ${hour}")
                Log.d("alarmItem", "alarmItem == null: minute ${minute}")
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
                Log.d("updatedAlarm", "alarmItem != null: updatedAlarm ${updatedAlarm}")
            }
            alarmAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()
    }




    fun scheduleAlarm(context: Context, alarmEntity: AlarmEntity){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    }



    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}