package uz.kabir.checkeyesight.history.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): Dao // Dao interface implementatsiya shu yerda bo'ladi

    companion object {
        fun initDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "user_database"
            ).allowMainThreadQueries().build()
        }
    }
}