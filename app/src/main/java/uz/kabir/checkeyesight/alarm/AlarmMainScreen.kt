package uz.kabir.checkeyesight.alarm

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.kabir.checkeyesight.custom.CustomPicker
import uz.kabir.checkeyesight.databinding.CustomDialogAlarmBinding
import uz.kabir.checkeyesight.databinding.FragmentAlarmMainScreenBinding
import android.provider.Settings

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

        // Android 12+ exact alarm ruxsati tekshiruvi (faqat bir marta, saqlashdan oldin)
//        if (!checkExactAlarmPermission(requireContext())) {
//            Toast.makeText(requireContext(), "Reminderlar uchun ruxsat bering", Toast.LENGTH_LONG).show()
//            dialog.dismiss()
//            return
//        }


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

            // Android 12+ exact alarm ruxsati tekshiruvi (faqat bir marta, saqlashdan oldin)
//            if (!checkExactAlarmPermission(requireContext())) {
//                Toast.makeText(
//                    requireContext(),
//                    "Reminderlar uchun ruxsat bering",
//                    Toast.LENGTH_LONG
//                ).show()
//                dialog.dismiss()
//                return@setOnClickListener
//            }

            lifecycleScope.launch {
                if (alarmItem == null) {
                    // YANGI ALARM yaratish
                    val newAlarm = AlarmEntity(
                        hour = hour,
                        minute = minute,
                        days = daysOfSetAlarm.toList()  // immutable List ga o'tkazamiz
                    )
                    val insertedId =
                        dao.insertAlarm(newAlarm).toInt()  // Room insert odatda Long qaytaradi

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

                    // 1. Eski alarmlarni bekor qilish
                    cancelAlarm(requireContext(), alarmItem)

                    // 2. Yangi parametrlar bilan qayta schedule qilish
                    scheduleAlarm(requireContext(), updatedAlarm)

                    // 3. Roomda yangilash
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
        }
        dialog.show()
    }


    fun scheduleAlarm(context: Context, alarmEntity: AlarmEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmEntity.days.forEach { day ->  // day: 1=Sunday, 2=Monday, ..., 7=Saturday
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, alarmEntity.hour)
                set(Calendar.MINUTE, alarmEntity.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                set(Calendar.DAY_OF_WEEK, day)

                // Agar o'tgan vaqt bo'lsa (shu kun o'tib ketgan bo'lsa), keyingi haftaga o'tkaz
                if (before(Calendar.getInstance())) {
                    add(Calendar.WEEK_OF_YEAR, 1)
                }
            }

            val requestCode = alarmEntity.id * 10 + day

            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("ALARM_ID", alarmEntity.id)
                // Agar reminder nomi bo'lsa, uni ham qo'shishingiz mumkin
                // putExtra("TITLE", alarmEntity.title)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    /**
     * Berilgan AlarmEntity uchun barcha kunlardagi scheduled alarmlarni bekor qiladi
     */
    private fun cancelAlarm(context: Context, alarmEntity: AlarmEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmEntity.days.forEach { day ->  // day: Calendar.MONDAY (2), TUESDAY (3), ..., SUNDAY (1)
            // scheduleAlarm da ishlatganimiz bilan bir xil requestCode
            val requestCode = alarmEntity.id * 10 + day

            val intent = Intent(context, AlarmReceiver::class.java).apply {
                // Extra qo'shish shart emas, lekin moslik uchun qo'shish mumkin
                putExtra("ALARM_ID", alarmEntity.id)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                // FLAG_NO_CREATE qo'shmasligimiz kerak, chunki cancel uchun PendingIntent mavjud bo'lishi shart
            )

            // Alarmni bekor qilish
            alarmManager.cancel(pendingIntent)
            // Qo'shimcha xavfsizlik uchun PendingIntent ni ham cancel qilish mumkin
            pendingIntent.cancel()
        }
    }
    private fun checkExactAlarmPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {  // Android 12+
            val alarmManager = context.getSystemService(AlarmManager::class.java)
            if (!alarmManager.canScheduleExactAlarms()) {
                // TO'G'RI ACTION: Settings dan olinadi
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    // Ba'zi hollarda package ni qo'shish yaxshi (tavsiya etiladi)
                    data = android.net.Uri.fromParts("package", context.packageName, null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    // Agar bu sahifa ochilmasa (ba'zi Xiaomi, Huawei va boshqa ROMlarda bo'ladi)
                    // Zapas: Umumiy app settings ga yo'naltirish
                    val fallbackIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = android.net.Uri.fromParts("package", context.packageName, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(fallbackIntent)
                    Toast.makeText(context, "Ilova sozlamalarida 'Alarms and reminders' ruxsatini yoqing", Toast.LENGTH_LONG).show()
                }
                return false
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}