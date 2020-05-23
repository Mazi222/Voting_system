package pl.edu.agh.votingapp.database.dao

import androidx.room.*
import pl.edu.agh.votingapp.database.entities.Answers

@Dao
interface AnswersDAO {
    @Query("SELECT * FROM answers WHERE votingId = :vid")
    fun loadAllAnswers(vid: Long): List<Answers>

    @Query("SELECT * FROM answers WHERE answerId = :aid")
    fun getAnswer(aid: Long): Answers

    @Query("UPDATE answers SET count = count + :value WHERE answerId = :aid") //Update
    fun updateCount(value: Long, aid: Long)

    @Query("UPDATE answers SET count = count + 1 WHERE answerId = :aid") //Update
    fun incrementCount(aid: Long)

    @Insert
    fun insertAll(answers: List<Answers>)

    @Insert
    fun insert(answer: Answers)

    @Delete
    fun delete(answer: Answers)
}