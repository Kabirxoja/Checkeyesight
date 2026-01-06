package uz.kabir.checkeyesight.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar

object AlarmScheduler {
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

            val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("ALARM_ID", alarmEntity.id)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                // Agar reminder nomi bo'lsa, uni ham qo'shishingiz mumkin
                // putExtra("TITLE", alarmEntity.title)
            }

            val alarmPendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
        }
    }

    /**
     * Berilgan AlarmEntity uchun barcha kunlardagi scheduled alarmlarni bekor qiladi
     */
    fun cancelAlarm(context: Context, alarmEntity: AlarmEntity) {
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

}