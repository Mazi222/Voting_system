package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user WHERE votingId = :vid")
    fun loadAllUsers(vid: Int): List<User>

    @Query("SELECT * FROM user WHERE userId = :uid AND votingId = :vid")
    fun getUser(uid: Int, vid: Int): User

    @Insert
    fun insertAll(users: List<User>)

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)
}