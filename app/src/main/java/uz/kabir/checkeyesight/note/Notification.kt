package uz.kabir.checkeyesight.note

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.main.MainActivity

class Notification {
    val channel_id = "com.example.checkeyesight"
    val channel_name = "android_channel"
    lateinit var pendingIntent:PendingIntent


    fun sendNotification(context: Context, message:String){

        val intent = Intent(context, MainActivity::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(channel_id, channel_name, importance)



            pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                
            } else {
                PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }



            val builder = NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.icon_chart)
                .setContentTitle("ALARM MANAGER - 1")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(notificationChannel)
            nm.notify(0, builder.build())
        }
        else
        {
            val builder = NotificationCompat.Builder(context)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("TIME IS COMING")
                .setContentText(message)
                .setContentIntent(PendingIntent.getActivities(context, 0, arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(0, builder.build())

        }
    }
}