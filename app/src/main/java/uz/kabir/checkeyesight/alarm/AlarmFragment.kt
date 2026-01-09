package uz.kabir.checkeyesight.alarm

import android.Manifest
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.kabir.checkeyesight.custom.CustomPicker
import uz.kabir.checkeyesight.databinding.CustomDialogAlarmBinding
import uz.kabir.checkeyesight.databinding.FragmentAlarmMainScreenBinding
import androidx.core.content.ContextCompat
import uz.kabir.checkeyesight.alarm.AlarmScheduler.cancelAlarm
import uz.kabir.checkeyesight.alarm.AlarmScheduler.scheduleAlarm
import uz.kabir.checkeyesight.alarm.db.AlarmDao
import uz.kabir.checkeyesight.alarm.db.AlarmDatabase
import uz.kabir.checkeyesight.alarm.db.AlarmEntity

class AlarmFragment : Fragment() {

    private var _binding: FragmentAlarmMainScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var alarmAdapter: AlarmAdapter
    private val alarmList = mutableListOf<AlarmEntity>()
    private lateinit var database: AlarmDatabase
    private lateinit var dao: AlarmDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationPermission()
        alarmNotificationChannel()

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

            val (hour, minute) = timePicker.getTime()


            lifecycleScope.launch {
                if (alarmItem == null) {
                    // YANGI ALARM yaratish
                    val newAlarm = AlarmEntity(
                        hour = hour,
                        minute = minute,
                        days = daysOfSetAlarm.toList()  // immutable List ga o'tkazamiz
                    )
                    val insertedId = dao.insertAlarm(newAlarm).toInt()  // Room insert odatda Long qaytaradi

                    // ID ni yangilab, schedule qilamiz
                    val alarmWithId = newAlarm.copy(id = insertedId)
                    scheduleAlarm(requireContext(), alarmWithId)

                    withContext(Dispatchers.Main) {
                        alarmList.add(alarmWithId)
                        alarmAdapter.notifyItemInserted(alarmList.lastIndex)
                    }

                } else {
                    // EDIT qilish — eski alarmlarni cancel qilamiz, yangisini schedule qilamiz
                    val updatedAlarm = alarmItem.copy(
                        hour = hour,
                        minute = minute,
                        days = daysOfSetAlarm.toList()
                    )

                    // Eski alarmlarni bekor qilish
                    cancelAlarm(requireContext(), alarmItem)

                    // Yangi parametrlar bilan qayta schedule qilish
                    scheduleAlarm(requireContext(), updatedAlarm)

                    // Roomda yangilash
                    dao.updateAlarm(updatedAlarm)

                    withContext(Dispatchers.Main) {
                        val index = alarmList.indexOfFirst { it.id == alarmItem.id }
                        if (index != -1) {
                            alarmList[index] = updatedAlarm
                            alarmAdapter.notifyItemChanged(index)
                        }
                    }

                }
            }
            dialog.dismiss()
        }
        dialog.show()
    }


    private fun alarmNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "alarm_channel",
                "Alarm Reminder",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alarm Notification"
            }
            val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun notificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
//                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100) -> DEPRECATED
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) /* Activity Result Launcher */
            }
        }
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Toast.makeText(
                    requireContext(),
                    "Notification permission granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Notification permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}