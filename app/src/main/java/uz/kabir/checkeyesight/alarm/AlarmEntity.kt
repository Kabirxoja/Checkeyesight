package uz.kabir.checkeyesight.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_table")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // bu o'zgarmaydi, lekin Room uchun konstruktor orqali kelishi kerak

    @ColumnInfo(name = "hour")
    val hour: Int,

    @ColumnInfo(name = "minute")
    val minute: Int,

    @ColumnInfo(name = "days")
    val days: List<Int>  // MutableList emas, oddiy List<Int> (xavfsizroq)
)