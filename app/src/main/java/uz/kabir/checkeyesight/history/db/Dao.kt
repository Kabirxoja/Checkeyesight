package uz.kabir.checkeyesight.history.db

import androidx.room.*
import androidx.room.Dao


@Dao
interface Dao{

    @Insert
    fun insertUser(user: HistoryEntity)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<HistoryEntity>

    @Delete
    fun deleteUser(user: HistoryEntity)

    @Update
    fun updateUsers(user: HistoryEntity)

}