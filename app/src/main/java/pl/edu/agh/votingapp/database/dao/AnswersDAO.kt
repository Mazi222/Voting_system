package pl.edu.agh.votingapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.edu.agh.votingapp.database.entities.Answers

@Dao
interface AnswersDAO {
    @Query("SELECT * FROM answers WHERE votingId = :vid")
    fun loadAllAnswers(vid: Int): List<Answers>

    @Query("SELECT * FROM answers WHERE votingId = :vid AND answerId = :aid")
    fun getAnswer(vid: Int, aid: Int): Answers

    @Insert
    fun insertAll(answers: List<Answers>)

    @Insert
    fun insert(answer: Answers)

    @Delete
    fun delete(answer: Answers)
}