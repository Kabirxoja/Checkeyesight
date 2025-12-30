package uz.kabir.checkeyesight.alarm

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TypeConvertor {

    @TypeConverter
    fun fromListToString(days: List<Int>?): String? {
        if (days == null) return null
        return Gson().toJson(days)
    }

    @TypeConverter
    fun fromStringToList(daysString: String?): List<Int> {
        if (daysString == null || daysString.isEmpty()) return mutableListOf()
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(daysString, listType) ?: mutableListOf()
    }
}