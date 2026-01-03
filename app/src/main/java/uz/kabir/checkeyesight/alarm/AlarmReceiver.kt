package uz.kabir.checkeyesight.alarm

import android.Manifest
import android.app.Notification
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
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.main.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val safeContext = context ?: return

        Log.d("ALARM_TEST", "Alarm fired at ${System.currentTimeMillis()}")

        val alarmId = intent?.getIntExtra("ALARM_ID", 0) ?: 0

        // Android 13+ notification permission tekshiruvi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    safeContext,
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
            safeContext,
            alarmId,
            openScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(safeContext, "alarm_channel")
            .setSmallIcon(R.drawable.icon_language)
            .setContentTitle("Reminder")
            .setContentText("It is time to break!")
            .setContentIntent(activityPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(safeContext)
            .notify(alarmId, notification)
    }
}
