package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.Answers

@Dao
interface AnswersDAO {
    @Query("SELECT * FROM answers WHERE votingId = :vid")
    fun loadAllAnswers(vid: Long): List<Answers>

    @Query("SELECT * FROM answers WHERE votingId = :vid AND answerId = :aid")
    fun getAnswer(aid: Long, vid: Long): Answers

    @Insert
    fun insertAll(answers: List<Answers>)

    @Insert
    fun insert(answer: Answers)

    @Delete
    fun delete(answer: Answers)
}