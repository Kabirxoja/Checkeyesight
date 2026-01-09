package uz.kabir.checkeyesight.alarm

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.MainActivity
import uz.kabir.checkeyesight.alarm.db.AlarmDatabase
import uz.kabir.checkeyesight.language.LanguageHelper.wrapContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val safeContext = context ?: return

        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                rescheduleAlarms(safeContext)
            }

            else -> {
                showNotification(safeContext, intent)
            }
        }
    }

    private fun rescheduleAlarms(context: Context) {
        val dao = AlarmDatabase.alarmDatabase(context).daoAlarm()
        val alarms = dao.getAllAlarms()
        CoroutineScope(Dispatchers.IO).launch {
            alarms.collect { alarmList ->
                alarmList.forEach { alarm ->
                    AlarmScheduler.scheduleAlarm(context, alarm)
                }
            }
        }
    }


    fun showNotification(context: Context, intent: Intent?) {
        Log.d("ALARM_TEST", "Alarm fired at ${System.currentTimeMillis()}")
        val localizedContext = wrapContext(context)

        val alarmId = intent?.getIntExtra("ALARM_ID", 0) ?: 0
        val alarmTimeMillis = intent?.getLongExtra("ALARM_TITLE", 0L) ?: 0

        val formattedTime = if (alarmTimeMillis != 0L) {
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(alarmTimeMillis))
        } else {
            ""
        }

        // Android 13+ notification permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.w("ALARM_TEST", "Notification permission not granted")
                return
            }
        }

        val openScreenIntent = Intent(context, MainActivity::class.java)
        openScreenIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        openScreenIntent.putExtra("OPEN_TAB_INDEX", 1)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            alarmId,
            openScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(R.drawable.icon_alarm)
            .setContentTitle(localizedContext.getString(R.string.alarm_title))
            .setContentText(formattedTime)
            .setContentIntent(activityPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(alarmId, notification)
    }


}
