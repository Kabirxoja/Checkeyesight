package uz.kabir.checkeyesight.alarm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeConvertor::class)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun daoAlarm(): AlarmDao

    companion object {

        @Volatile
        private var INSTANCE: AlarmDatabase? = null

        fun alarmDatabase(context: Context): AlarmDatabase {
            return INSTANCE ?: synchronized(this) {
                /*  Bu blokka bir vaqtning o‘zida faqat bitta thread kira oladi */
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmDatabase::class.java,
                    "alarm_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }

            /*
            Agar 2 thread bir paytda shu joyga yetsa:
            Ikkalasi ham INSTANCE == null deb o‘ylaydi
            Ikkalasi ham DB yaratadi ❌
            synchronized(this) buni oldini oladi.
             */

        }
    }

    /*
    synchronized(X) {
    // critical section
}
👉 Ma’nosi:
    “X obyektini qulf (lock) sifatida ishlat va shu blokka bir vaqtda faqat bitta thread kirsin”
     */
}