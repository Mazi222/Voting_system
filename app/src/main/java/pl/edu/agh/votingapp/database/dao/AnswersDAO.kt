package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.Voting

@Dao
interface AnswersDAO {
    @Query("SELECT * FROM voting")
    fun loadAllAnswers(vid: Int): List<Voting>

    @Query("SELECT * FROM voting WHERE votingId = :vid")
    fun getAnswer(vid: Int): Voting

    @Insert
    fun insertAll(votings: List<Voting>)

    @Insert
    fun insert(voting: Voting)

    @Delete
    fun delete(voting: Voting)
}