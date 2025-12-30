package uz.kabir.checkeyesight.history;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int,
    @ColumnInfo(name = "leftEyes")
    val leftEye:String,
    @ColumnInfo(name = "rightEye")
    var rightEye:String,
    @ColumnInfo(name = "dateFormat")
    val dateFormat:String

)