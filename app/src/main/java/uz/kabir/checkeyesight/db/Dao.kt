package uz.kabir.checkeyesight.db

import androidx.room.*
import androidx.room.Dao


@Dao
interface Dao {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUsers(user: User)


}